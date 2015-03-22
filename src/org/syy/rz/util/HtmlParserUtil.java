package org.syy.rz.util;
import info.monitorenter.cpdetector.io.*;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by syy on 2014/7/10.
 */
public class HtmlParserUtil {

    /**
     * 清除html标签
     * @param html
     * @return
     */
    public static String clearTags(String html) {
        html = html.trim();
        html = html.replace("&nbsp;", " ");
        Pattern pa = Pattern.compile("<[w]*[^>]*>");
        Matcher match = pa.matcher(html);
        while (match.find()) {
            String s = match.group();
            int index = html.indexOf(s);
            while (-1 != index) {
                html = html.substring(0, index) + html.substring(s.length()+index);
                index = html.indexOf(s);
            }
        }
        return html;
    }

    /**
     * 获得url的根域名
     * @param url
     * @return
     */
    public static String findDomain(String url) {
        String tmp = null;
        String prifex = "http://";
        if (url.startsWith("http://")) {
            tmp = url.substring(7);
        } else if (url.startsWith("https://")) {
            tmp = url.substring(8);
            prifex = "https://";
        } else {
            return null;
        }
        if (tmp.indexOf("/") == -1) {
            return url;
        }
        tmp = tmp.substring(0, tmp.indexOf("/"));
        return prifex + tmp;
    }

    /**
     * 获得页面编码
     * @param doc
     * @return
     */
    public static String getCharset(Document doc) {
        String charset = null;

        Element head = doc.head();
        Elements metas = head.select("meta");

        /*
         * html5 charset抽取
         */
        for (Element meta : metas) {
            if (StringUtils.isNotEmpty(meta.attr("charset"))) {
                charset = meta.attr("charset");
                break;
            }
        }

        /*
         * 非html5 charset抽取
         */
        if (null == charset) {
            for (Element meta : metas) {
                if (StringUtils.isNotEmpty(meta.attr("content"))) {
                    String temp = meta.attr("content");
                    if (temp.indexOf("charset=") != -1) {
                        charset = temp.substring(temp.indexOf("charset=")+8);
                        if (charset.indexOf(";") != -1) {
                            charset = charset.substring(0, charset.indexOf(";"));
                        }
                        break;
                    }
                }
            }
        }

        /*
         * 无法抽取charset，返回null
         */
        if (null == charset || charset.trim().equals("")) {
            CodepageDetectorProxy detector =   CodepageDetectorProxy.getInstance();
            /*-------------------------------------------------------------------------
              ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
              指示是否显示探测过程的详细信息，为false不显示。
            ---------------------------------------------------------------------------*/
            detector.add(new ParsingDetector(false));
            /*--------------------------------------------------------------------------
              JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
              测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
              再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
            ---------------------------------------------------------------------------*/
            detector.add(JChardetFacade.getInstance());
            //ASCIIDetector用于ASCII编码测定
            detector.add(ASCIIDetector.getInstance());
            //UnicodeDetector用于Unicode家族编码的测定
            detector.add(UnicodeDetector.getInstance());
            java.nio.charset.Charset cs = null;
            try {
                cs = detector.detectCodepage(new BufferedInputStream(new ByteArrayInputStream(doc.html().getBytes())), 50);
            } catch (Exception ex) {
                charset = "utf-8";
                System.out.println("DefaultHtmlParser:" + ex.getMessage());
            }

            if (cs != null) {
                charset = cs.name();
//                System.out.println("DefaultHtmlParser:探测到页面编码为" + charset);
            }
        }

        return charset==null?"utf-8":charset.trim().toLowerCase();
    }
}
