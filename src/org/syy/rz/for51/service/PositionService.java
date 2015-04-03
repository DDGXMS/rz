package org.syy.rz.for51.service;

import javafx.geometry.Pos;
import org.apache.http.client.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.syy.rz.for51.bus.SystemBus;
import org.syy.rz.for51.crawl.HttpClientFor51Job;
import org.syy.rz.for51.crawl.HttpClientUtils;
import org.syy.rz.for51.entity.Position;
import org.syy.rz.for51.entity.User;
import org.syy.rz.util.SugarMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/4/3.
 */
public class PositionService {

    public List<Position> loadPosition(int index) {
        Map<String, String> params = null;
        if(SystemBus.instance().getPositionParams() == null) {
            params = packageFirstParams();
            SystemBus.instance().setPositionParams(params);
        }


        // 拼页码
        params.put("pagerBottom$txtGO",String.valueOf(index));

        String html = HttpClientUtils.httpPost("http://ehire.51job.com/Jobs/JobSearchPost.aspx", params, SystemBus.instance().getLoginClient());
        Document doc = Jsoup.parse(html);

        // 设置总的职位数
        SystemBus.instance().setTotalPosition(Integer.parseInt(doc.getElementById("span_TotalRecords").text()));

        return getPageData(doc);
    }

    private List<Position> getPageData(Document doc) {
        Elements elements = doc.getElementsByClass("ehireGrid");
        List<Position> positionList = new ArrayList<>(elements.size());

        for (int i=0; i<elements.size(); i++) {
            Elements tds = elements.get(i).getElementsByTag("td");
            if (tds.size() < 7) {
                continue;
            }

            String positionName = tds.get(1).getElementsByTag("a").get(0).text();
            String company = tds.get(4).text();
            String workPlace = tds.get(5).text();
            String publishDate = tds.get(6).text();
            String freshDate = tds.get(7).text();
            String endDate = tds.get(8).text();
            boolean canFresh = false;
            if (tds.get(10).getElementsByClass("a_refresh").size() > 0) {
                canFresh = true;
            }

            Position position = new Position(positionName, company, workPlace, publishDate, freshDate, endDate, canFresh);
            positionList.add(position);
        }

        return positionList;
    }

    /**
     * 封装第一次请求职位信息
     * @return
     */
    private Map<String, String> packageFirstParams() {
        String html = HttpClientUtils.httpGet("http://ehire.51job.com/Jobs/JobSearchPost.aspx", SystemBus.instance().getLoginClient());
        Document defaultDoc = Jsoup.parse(html);
        Map<String, String> params = SugarMap.stringMap(
                "__EVENTTARGET", "",
                "__EVENTARGUMENT", "",
                "__LASTFOCUS", "",
                "__VIEWSTATE", defaultDoc.getElementById("__VIEWSTATE").val(),
                "MainMenuNew1$CurMenuID", "MainMenuNew1_imgJob|sub2",
                "txtJobName", "",
                "dlSearchFiled", "CJOBNAME",
                "COID", "",
                "DIVID", "",
                "dlHTStatus", "ALL",
                "dlTERM", "",
                "pagerBottom$txtGO", "1",
                "pagerBottom$lbtnGO", "",
                "WCBigAreaCode1$2", "rdbGroup2_1",
                "radjob", defaultDoc.getElementById("radSelactnow").val(),
                "ctmid", defaultDoc.getElementById("ctmid").val(),
                "Hidjob", "",
                "hidISEN", "1",
                "HidFlag", "N",
                "hidLeft", "",
                "hidTop", "",
                "hidJobID", "",
                "hidJobFlag", "",
                "HidOrderBy", "",
                "hidAjaxJobId", "",
                "hidRePublishType", "",
                "hidSearchHidden", "",
                "hidYellowTip", "0",
                "hidShowJobAd", "0"
        );

        return params;
    }

    public static void main(String[] args) throws IOException {
        HttpClient client = HttpClientFor51Job.login(new User("德佑-2014", "德佑研发中心总", "dooioo2015"));
        SystemBus.instance().setLoginClient(client);

        PositionService positionService = new PositionService();
        List<Position> positions = positionService.loadPosition(5);
        for (Position p : positions) {
            System.out.println(p);
        }
    }

}
