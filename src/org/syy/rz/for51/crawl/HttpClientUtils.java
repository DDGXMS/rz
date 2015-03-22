package org.syy.rz.for51.crawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.syy.rz.util.HtmlParserUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 针对需要模拟登陆而编写的工具类
 * Created by syy on 2015/3/22.
 */
public class HttpClientUtils {

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
                String charset = "utf-8";
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
            Iterator<Map.Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
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
}
