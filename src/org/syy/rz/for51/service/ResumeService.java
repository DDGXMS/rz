package org.syy.rz.for51.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.syy.rz.for51.bus.SystemBus;
import org.syy.rz.for51.crawl.HttpClientUtils;
import org.syy.rz.for51.entity.Resume;
import org.syy.rz.for51.excel.ExcelUtilFor51;
import org.syy.rz.util.SugarMap;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简历业务逻辑
 * 包括简历展示、一键导出等
 * Created by syy on 2015/3/22.
 */
public class ResumeService {

    /**
     * 获得第一页数据
     * @return
     */
    public List<Resume> getFirstResumeList() {
        Map<String, String> params = null;
        if(SystemBus.instance().getFirstParams() == null) {
            params = packageFirstParams();
            SystemBus.instance().setFirstParams(params);
        }

        // 根据参数获得第一页
        String html = HttpClientUtils.httpPost("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", params, SystemBus.instance().getLoginClient());
        Document doc = Jsoup.parse(html);
        // 设置其它页请求参数
        SystemBus.instance().setOtherParams(packageOtherParams(params, doc));
        // 设置总的简历数
        SystemBus.instance().setTotalResume(Integer.parseInt(doc.getElementById("span_TotalRecords").text()));

        return getPageData(doc);
    }

    /**
     * 获得其他页数据
     * @param pageNo
     * @return
     */
    public List<Resume> getOtherResumeList(int pageNo) {
        if (pageNo < 2) {
            return null;
        }

        Map<String, String> params = SystemBus.instance().getOtherParams();
        params.put("pagerBottom$txtGO",String.valueOf(pageNo));
        String html = HttpClientUtils.httpPost("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", params, SystemBus.instance().getLoginClient());
        Document doc = Jsoup.parse(html);

        return getPageData(doc);
    }

    /**
     * 一键导出简历
     */
    public void exportOneKey() {
        List<Resume> totalData = new ArrayList<Resume>();
        totalData.addAll(this.getFirstResumeList());

        for (int i = 2; i < SystemBus.instance().getPageNum(); i++) {
            totalData.addAll(this.getOtherResumeList(i));
        }

        // 导出
        HSSFWorkbook workbook = ExcelUtilFor51.exportTotalResume(totalData);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("d:/简历1.xls");
            workbook.write(fos);
            fos.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 更具DOM文档获得列表中的简历信息
     * @param doc
     * @return
     */
    private List<Resume> getPageData(Document doc) {
        List<Resume> data = new ArrayList<>();
        for (int i=1; i<=(doc.select("#divGridData>div>table>tbody>tr").size()-1)/2; i++) {
            Element tr = doc.getElementById("trBaseInfo_" + i);
            Elements tds = tr.getElementsByTag("td");
            if(tds.size() < 14) {
                continue;
            }
            String matchDegree = tds.get(1).text();
            String name = tds.get(3).select(".rsmnameA a").get(0).text();
            String position = tds.get(4).text();
            String company = tds.get(5).text();
            String workPlace = tds.get(6).text();
            String age = tds.get(8).text();
            String education = tds.get(9).text();
            String workYears = tds.get(10).text();
            String sendTime = tds.get(11).text();

            System.out.println(matchDegree + "\t" + name + "\t" + position + "\t" + company + "\t" + workPlace + "\t" + age + "  " + education + "\t" + workYears + "\t" + sendTime);
            data.add(new Resume(matchDegree, name, position, company, workPlace, age, education, workYears, sendTime));
        }
        return data;
    }

    /**
     * 封装简历第一页请求参数
     * @return
     */
    private Map<String, String> packageFirstParams() {
        String html = HttpClientUtils.httpGet("http://ehire.51job.com/Inbox/InboxRecentEngine.aspx?Style=1&ShowAD=1", SystemBus.instance().getLoginClient());
        Document defaultDoc = Jsoup.parse(html);
        Map<String, String> params = SugarMap.stringMap(
                "btnEliminateDup$btnEliminateDup", "简历消重",
                "__EVENTTARGET", "",
                "__EVENTARGUMENT", "",
                "__LASTFOCUS", "",
                "__VIEWSTATE", defaultDoc.getElementById("__VIEWSTATE").val(),
                "MainMenuNew1$CurMenuID", "MainMenuNew1_imgApp|sub3",
                "hidTab", "",
                "ctrlSerach$ddlSearchName", "",
                "ctrlSerach$dropCoid", "",
                "ctrlSerach$dropDivision", "",
                "ctrlSerach$hidSearchID", defaultDoc.getElementById("ctrlSerach_hidSearchID").val(),
                "ctrlSerach$KEYWORD", "--可选择“工作、项目、职务、学校”关键字--",
                "ctrlSerach$KEYWORDTYPE", "1",
                "ctrlSerach$AREA$Text", "选择/修改",
                "ctrlSerach$AREA$Value", "",
                "ctrlSerach$SEX", "2",
                "ctrlSerach$TopDegreeFrom", "",
                "ctrlSerach$TopDegreeTo", "",
                "ctrlSerach$WORKFUN1$Text", "选择/修改",
                "ctrlSerach$WORKFUN1$Value", "",
                "ctrlSerach$WORKINDUSTRY1$Text", "选择/修改",
                "ctrlSerach$WORKINDUSTRY1$Value", "",
                "ctrlSerach$WorkYearFrom", "0",
                "ctrlSerach$WorkYearTo", "99",
                "ctrlSerach$TOPMAJOR$Text", "选择/修改",
                "ctrlSerach$TOPMAJOR$Value", "",
                "ctrlSerach$AgeFrom", "",
                "ctrlSerach$AgeTo", "",
                "ctrlSerach$txtUserID", "-多个简历ID用空格隔开-",
                "ctrlSerach$txtMobile", "",
                "ctrlSerach$txtName:", "",
                "ctrlSerach$txtMail", "",
                "ctrlSerach$hidTwoValue", "",
                "ctrlSerach$radFandF", "0",
                "ctrlSerach$txtSearchName", "",
                "dropRecentResumeRange", "30",
                "pagerBottom$txtGO", "1",
                "pagerBottom$lbtnGO", " ",
                "cbxColumns$0", "LABELID",
                "cbxColumns$2", "AGE",
                "cbxColumns$4", "TOPDEGREE",
                "cbxColumns$5", "WORKYEAR",
                "cbxColumns$19", "SENDDATE",
                "Keyword1", "",
                "1", "SortAsc1",
                "Keyword2", "",
                "2", "SortAsc2",
                "Keyword3", "",
                "3", "SortAsc3",
                "inboxTypeFlag", "5",
                "ShowFrom", "1",
                "hidEvents", "",
                "hidSeqID", "",
                "hidUserID", "",
                "hidFolder", "EMP",
                "BAK2INT", "",
                "hidJobID", "",
                "SubmitValue", "",
                "hidDisplayType", "0",
                "hidOrderByCol", "",
                "hidSearchHidden", "",
                "hidBatchBtn", "",
                "hidProcessType", "",
                "hidUserDistinct", "0",
                "hidEngineCvlogIds", defaultDoc.getElementById("hidEngineCvlogIds").val());

        return params;
    }

    /**
     * 封装其他页请求参数
     * @param params    第一页请求参数
     * @param firstDoc  第一页页面结构
     * @return
     */
    private Map<String, String> packageOtherParams(Map<String, String> params, Document firstDoc) {
        params.put("__VIEWSTATE",firstDoc.getElementById("__VIEWSTATE").val());
        params.put("ctrlSerach$hidSearchID",firstDoc.getElementById("ctrlSerach_hidSearchID").val());
        params.put("hidEngineCvlogIds", firstDoc.getElementById("hidEngineCvlogIds").val());
        params.put("hidUserDistinct","1");
        params.remove("btnEliminateDup$btnEliminateDup");
        return params;
    }
}
