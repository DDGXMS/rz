package org.syy.rz.crawl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.syy.rz.entity.Rz;
import org.syy.rz.excel.ExcelUtil;
import org.syy.rz.util.HtmlParserUtil;
import org.syy.rz.util.SugarMap;

/**
 * TODO
 * 
 * @author syy
 * @since 2014-11-25 下午3:29:12
 * @version 1.0
 */
public class Job51Test {

    /**
     * post请求，有参数，无代理
     * 
     * @param url
     * @param params
     * @return
     * @throws java.io.IOException
     * @throws org.apache.http.client.ClientProtocolException
     */
    public static HttpClient login51Job(String name1, String name2, String password)
            throws ClientProtocolException, IOException {

        Document doc = Jsoup.parse(new URL("http://ehire.51job.com/MainLogin.aspx"), 5000);
        String accessKey = doc.getElementById("hidAccessKey").val();
        String langType = doc.getElementById("hidLangType").val();
        String sc = doc.getElementById("fksc").val();
        String ec = doc.getElementById("hidEhireGuid").val();
        System.out.println(accessKey + "," + langType + "," + sc + "," + ec);

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://ehirelogin.51job.com/Member/UserLogin.aspx");
        httpPost.setHeader("Host", "ehirelogin.51job.com");
        httpPost.setHeader("Origin", "http://ehire.51job.com");
        httpPost.setHeader("Referer", "http://ehire.51job.com/MainLogin.aspx");
        Map<String, String> params = SugarMap.stringMap("ctmName",
                URLEncoder.encode(name1, "utf-8"), "userName", URLEncoder.encode(name2, "utf-8"),
                "password", password, "isRememberMe", "true", "checkCode", "", "oldAccessKey",
                accessKey, "langtype", langType, "sc", sc, "ec", ec);
        List<BasicNameValuePair> nvps = null;
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<BasicNameValuePair>(params.size());
            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            while(it.hasNext()) {
                Entry<String, String> entry = it.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        if (null != nvps) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        HttpResponse response = httpClient.execute(httpPost);
        System.out.println(response.getStatusLine());
        String location = response.getFirstHeader("Location").getValue();
        System.out.println(location);
        HttpGet httpGet = new HttpGet(location);
        response = httpClient.execute(httpGet);
        System.out.println(response.getStatusLine());
        Document loginedDoc = Jsoup.parse(EntityUtils.toString(response.getEntity()));
        if (loginedDoc.select("#Head1 title").text().equals("在线用户管理")) {
            System.out.println("强制下线");
            System.out.println("http://ehire.51job.com/Member/"+loginedDoc.getElementById("form1").attr("action"));
            httpPost = new HttpPost("http://ehire.51job.com/Member/"+loginedDoc.getElementById("form1").attr("action"));
            params = SugarMap.stringMap("__EVENTTARGET", "gvOnLineUser", "__EVENTARGUMENT", "KickOut$0", "__VIEWSTATE", loginedDoc.getElementById("__VIEWSTATE").val());
            if (params != null && params.size() > 0) {
                nvps = new ArrayList<BasicNameValuePair>(params.size());
                Iterator<Entry<String, String>> it = params.entrySet().iterator();
                while(it.hasNext()) {
                    Entry<String, String> entry = it.next();
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            if (null != nvps) {
                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());
//            for (Header head : response.getAllHeaders()) {
//                System.out.println(head.getName() + ":" + head.getValue());
//            }
            httpClient = (CloseableHttpClient) Job51Test.login51Job(name1, name2, password);
//            location = response.getFirstHeader("Location").getValue();
//            System.out.println(location);
//            httpGet = new HttpGet(location);
//            response = httpClient.execute(httpGet);
//            System.out.println(response.getStatusLine());
//            System.out.println(EntityUtils.toString(response.getEntity()));
        }
        return httpClient;
    }

    public static String httpGet(String url, HttpClient client) {

        HttpGet httpGet = new HttpGet(url);
//        RequestConfig config = RequestConfig.custom().setSocketTimeout(4000)
//                .setConnectTimeout(4000).build();
//        httpGet.setConfig(config);
        try {
            // 执行get请求
            System.out.println("crawl --> :" + url);
            HttpResponse response = client.execute(httpGet);
            System.out.println("staus --> :" + response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                // 读内容
                String charset = "";
                byte[] bytes = EntityUtils.toByteArray(entity);
                if (entity != null) {
                    charset = EntityUtils.getContentCharSet(entity);
                }
                if (StringUtils.isEmpty(charset)) {
                    charset = HtmlParserUtil.getCharset(Jsoup.parse(new String(bytes)));
                }
                return new String(bytes, charset);
            } else {
                System.out.println(response.getStatusLine().toString());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
    
    public static String httpPost(String url, Map<String, String> params, HttpClient httpClient) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Host","ehire.51job.com");
        httpPost.setHeader("Origin","http://ehire.51job.com");
        httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.65 Safari/537.36");
        httpPost.setHeader("Referer","http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1");
        httpPost.setHeader("RA-Sid","B4AD8A06-20140916-102235-ddf9ff-9a4679");
        httpPost.setHeader("RA-Ver","2.7.1");
        httpPost.setHeader("Accept-Encoding","gzip, deflate");
        List<BasicNameValuePair> nvps = null;
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<BasicNameValuePair>(params.size());
            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> entry = it.next();
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
         
        if (null != nvps) {
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
         
        HttpResponse httpResponse = null;
        String data = "";
        try{
            httpResponse = httpClient.execute(httpPost);
            System.out.println(httpResponse.getStatusLine());
            for (Header head : httpResponse.getAllHeaders()) {
                System.out.println(head.getName() + ":" + head.getValue());
            }
            data =  EntityUtils.toString(httpResponse.getEntity());
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void main(String[] args) throws ClientProtocolException, IOException {
        
//        HttpClient client = Job51Test.login51Job("德佑-2014", "德佑研发中心总", "dooioo2015");
        HttpClient client = Job51Test.login51Job("德佑-2014", "经纪研发中心总", "dooioo2015");
        String res = Job51Test.httpGet("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", client);
//        System.out.println(res);
        Document doc = Jsoup.parse(res);
        
        Map<String, String> params = SugarMap.stringMap(
                "btnEliminateDup$btnEliminateDup", "简历消重",
                "__EVENTTARGET","",
                "__EVENTARGUMENT","",
                "__LASTFOCUS","",
                "__VIEWSTATE",doc.getElementById("__VIEWSTATE").val(),
                "MainMenuNew1$CurMenuID", "MainMenuNew1_imgApp|sub3",
                "hidTab","",
                "ctrlSerach$ddlSearchName","",
                "ctrlSerach$dropCoid","",
                "ctrlSerach$dropDivision","",
                "ctrlSerach$hidSearchID",doc.getElementById("ctrlSerach_hidSearchID").val(),
                "ctrlSerach$KEYWORD","--可选择“工作、项目、职务、学校”关键字--",
                "ctrlSerach$KEYWORDTYPE","1",
                "ctrlSerach$AREA$Text","选择/修改",
                "ctrlSerach$AREA$Value","",
                "ctrlSerach$SEX","2",
                "ctrlSerach$TopDegreeFrom","",
                "ctrlSerach$TopDegreeTo","",
                "ctrlSerach$WORKFUN1$Text","选择/修改",
                "ctrlSerach$WORKFUN1$Value","",
                "ctrlSerach$WORKINDUSTRY1$Text","选择/修改",
                "ctrlSerach$WORKINDUSTRY1$Value","",
                "ctrlSerach$WorkYearFrom","0",
                "ctrlSerach$WorkYearTo","99",
                "ctrlSerach$TOPMAJOR$Text","选择/修改",
                "ctrlSerach$TOPMAJOR$Value","",
                "ctrlSerach$AgeFrom","",
                "ctrlSerach$AgeTo","",
                "ctrlSerach$txtUserID","-多个简历ID用空格隔开-",
                "ctrlSerach$txtMobile","",
                "ctrlSerach$txtName:","",
                "ctrlSerach$txtMail","",
                "ctrlSerach$hidTwoValue","",
                "ctrlSerach$radFandF","0",
                "ctrlSerach$txtSearchName","",
                "dropRecentResumeRange","30",
                "pagerBottom$txtGO","1",
                "pagerBottom$lbtnGO"," ",
                "cbxColumns$0","LABELID",
                "cbxColumns$2","AGE",
                "cbxColumns$4","TOPDEGREE",
                "cbxColumns$5","WORKYEAR",
                "cbxColumns$19","SENDDATE",
                "Keyword1","",
                "1","SortAsc1",
                "Keyword2","",
                "2","SortAsc2",
                "Keyword3","",
                "3","SortAsc3",
                "inboxTypeFlag","5",
                "ShowFrom","1",
                "hidEvents","",
                "hidSeqID","",
                "hidUserID","",
                "hidFolder","EMP",
                "BAK2INT","",
                "hidJobID","",
                "SubmitValue","",
                "hidDisplayType","0",
                "hidOrderByCol","",
                "hidSearchHidden","",
                "hidBatchBtn","",
                "hidProcessType","",
                "hidUserDistinct","0",
                "hidEngineCvlogIds",doc.getElementById("hidEngineCvlogIds").val());
        List<Rz> data = new ArrayList<Rz>();
        res = Job51Test.httpPost("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", params, client);
        doc = Jsoup.parse(res);
        for (int i=1; i<=(doc.select("#divGridData>div>table>tbody>tr").size()-1)/2; i++) {
            Element tr = doc.getElementById("trBaseInfo_" + i);
            Elements tds = tr.getElementsByTag("td");
            if(tds.size() < 14) {
                continue;
            }
            String pipei = tds.get(1).text();
            String name = tds.get(3).select(".rsmnameA a").get(0).text();
            String postion = tds.get(4).text();
            String company = tds.get(5).text();
            String workPlace = tds.get(6).text();
            String age = tds.get(8).text();
            String education = tds.get(9).text();
            String workYears = tds.get(10).text();
            String sendTime = tds.get(11).text();
            
            System.out.println(pipei + "\t" + name + "\t" + postion + "\t" + company + "\t" + workPlace + "\t" + age + "  " + education + "\t" + workYears + "\t" + sendTime);
            data.add(new Rz(pipei, name, postion, company, workPlace, age, education, workYears, sendTime));
        }
        
        
        int total = Integer.parseInt(doc.getElementById("span_TotalRecords").text());
        System.out.println("total" + total);
        params.put("__VIEWSTATE",doc.getElementById("__VIEWSTATE").val());
        params.put("ctrlSerach$hidSearchID",doc.getElementById("ctrlSerach_hidSearchID").val());
        params.put("hidEngineCvlogIds", doc.getElementById("hidEngineCvlogIds").val());
        params.put("hidUserDistinct","1");
        params.remove("btnEliminateDup$btnEliminateDup");
        
        int totalpage = total%50==0? total/50 : total/50+1;
        for (int p=2; p<=totalpage; p++) {
            params.put("pagerBottom$txtGO",String.valueOf(p));
            res = Job51Test.httpPost("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", params, client);
            doc = Jsoup.parse(res);
            for (int i=1; i<=(doc.select("#divGridData>div>table>tbody>tr").size()-1)/2; i++) {
                Element tr = doc.getElementById("trBaseInfo_" + i);
                Elements tds = tr.getElementsByTag("td");
                if(tds.size() < 14) {
                    continue;
                }
                String pipei = tds.get(1).text();
                String name = tds.get(3).select(".rsmnameA a").get(0).text();
                String postion = tds.get(4).text();
                String company = tds.get(5).text();
                String workPlace = tds.get(6).text();
                String age = tds.get(8).text();
                String education = tds.get(9).text();
                String workYears = tds.get(10).text();
                String sendTime = tds.get(11).text();
                
                data.add(new Rz(pipei, name, postion, company, workPlace, age, education, workYears, sendTime));
                System.out.println(pipei + "\t" + name + "\t" + postion + "\t" + company + "\t" + workPlace + "\t" + age + "  " + education + "\t" + workYears + "\t" + sendTime);
            }
        }
        
        HSSFWorkbook workbook = ExcelUtil.exportExcelForStudent(data);
        FileOutputStream fos = new FileOutputStream("d:/简历1.xls");
        workbook.write(fos);
        fos.flush();
        fos.close();
    }
}
