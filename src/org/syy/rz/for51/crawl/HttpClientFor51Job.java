package org.syy.rz.for51.crawl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.syy.rz.for51.entity.User;
import org.syy.rz.util.SugarMap;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 针对51joib编写的模拟登陆
 * Created by Administrator on 2015/3/22.
 */
public class HttpClientFor51Job {

    /**
     * 下线
     * @param user
     * @return
     * @throws IOException
     */
    public static HttpClient login(User user) throws IOException {

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
                URLEncoder.encode(user.getName(), "utf-8"), "userName", URLEncoder.encode(user.getGroup(), "utf-8"),
                "password", user.getPassword(), "isRememberMe", "true", "checkCode", "", "oldAccessKey",
                accessKey, "langtype", langType, "sc", sc, "ec", ec);
        List<BasicNameValuePair> nvps = null;
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<BasicNameValuePair>(params.size());
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
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
            forcedOffline(params, loginedDoc, httpClient);
            httpClient = (CloseableHttpClient) HttpClientFor51Job.login(user);
        }
        return httpClient;
    }

    /**
     * 强制下线
     * @param params
     * @param loginedDoc
     * @param httpClient
     */
    private static void forcedOffline(Map<String, String> params, Document loginedDoc, CloseableHttpClient httpClient) {
        List<BasicNameValuePair> nvps = null;

        System.out.println("强制下线");
        HttpPost httpPost = new HttpPost("http://ehire.51job.com/Member/"+loginedDoc.getElementById("form1").attr("action"));
        params = SugarMap.stringMap("__EVENTTARGET", "gvOnLineUser", "__EVENTARGUMENT", "KickOut$0", "__VIEWSTATE", loginedDoc.getElementById("__VIEWSTATE").val());
        if (params != null && params.size() > 0) {
            nvps = new ArrayList<BasicNameValuePair>(params.size());
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while(it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
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

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            System.out.println(response.getStatusLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
