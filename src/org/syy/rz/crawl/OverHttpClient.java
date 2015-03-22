package org.syy.rz.crawl;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.syy.rz.entity.ProxyAddress;
import org.syy.rz.util.HtmlParserUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/** 
 * 对httpclient的封装
 * get，post等方法
 *
 * @author syy
 * @date 2014-11-3 下午9:58:54 
 * @version 1.0
 */ 
public class OverHttpClient {
	
	private static Logger log = Logger.getLogger(OverHttpClient.class.getName());

	/** 
	 * get请求，无代理服务器
	 * @param url
	 * @return    
	 */ 
	@Deprecated
	public static String httpGetOld(String url) {
		return httpGet(url, null);
	}
	
    /** 
     * get请求，含代理服务器
     * @param url	请求url
     * @param add	代理服务器ip和port
     * @return    
     */ 
	@Deprecated
    public static String httpGetOld(String url, ProxyAddress add) {
    	HttpClient httpClient = new DefaultHttpClient();
    	
    	/*
    	 * 设置代理
    	 */
    	if (null != add) {
    		HttpHost proxy = new HttpHost(add.getIp(), add.getPort());
    		log.info(add.getIp() + ":" + add.getPort());
    		httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
    	}
        
        HttpGet request = new HttpGet(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(5000).setConnectTimeout(5000).build();
        request.setConfig(requestConfig);
        
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");   
        
        try {
        	log.info("crawl --> :" + url);
            HttpResponse response = httpClient.execute(request);
            log.debug("staus --> :" + response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                /*
                 * 判断charset，防止乱码
                 */
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
                log.warn(response.getStatusLine().toString());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            httpClient.getConnectionManager().shutdown();
        }

        return null;
    }
    
    /** 
     * post请求，无参，无代理服务器
     * @param url
     * @return    
     */ 
    public static String httpPost(String url) {
    	return httpPost(url, null);
    }
    
    /** 
     * post请求，有参数，无代理
     * @param url
     * @param params
     * @return    
     */ 
    public static String httpPost(String url, Map<String, String> params) {
    	CloseableHttpClient httpClient = HttpClients.createDefault();
    	HttpPost httpPost = new HttpPost(url);
    	 
    	List<BasicNameValuePair> nvps = null;
    	if (params != null && params.size() > 0) {
    		nvps = new ArrayList<BasicNameValuePair>(params.size());
    		Iterator<Entry<String, String>>	it = params.entrySet().iterator();
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
    	 
    	CloseableHttpResponse httpResponse = null;
    	String data = "";
    	try{
    		httpResponse = httpClient.execute(httpPost);
    		log.info(httpResponse.getStatusLine());
    		data =  EntityUtils.toString(httpResponse.getEntity());
    	} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
    		try {
				httpResponse.close();
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    	return data;
    }
    
    public static String httpGet(String url) {
    	return httpGet(url, null);
    }
    public static String httpGet(String url, ProxyAddress add) {

        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url);
        if (null != add) {
        	// 依次是代理地址，代理端口号，协议类型
        	HttpHost proxy = new HttpHost(add.getIp(), add.getPort(), "http");
        	RequestConfig config = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).setProxy(proxy).build();
        	httpGet.setConfig(config);
        }
        try {
            // 执行get请求
            log.info("crawl --> :" + url);
            HttpResponse response = closeableHttpClient.execute(httpGet);
            log.info("staus --> :" + response.getStatusLine());
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
                log.warn(response.getStatusLine().toString());
            }
        } catch (IOException e) {
        	log.error(e.getMessage());
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
    
    public static byte[] httpGetStream(String url, ProxyAddress add) {

        // 创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

        HttpGet httpGet = new HttpGet(url);
        if (null != add) {
            // 依次是代理地址，代理端口号，协议类型
            HttpHost proxy = new HttpHost(add.getIp(), add.getPort(), "http");
            RequestConfig config = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).setProxy(proxy).build();
            httpGet.setConfig(config);
        }
        try {
            // 执行get请求
            log.info("crawl --> :" + url);
            HttpResponse response = closeableHttpClient.execute(httpGet);
            log.info("staus --> :" + response.getStatusLine());
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toByteArray(entity);
            } else {
                log.warn(response.getStatusLine().toString());
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            try {
                // 关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return null;
    }
}
