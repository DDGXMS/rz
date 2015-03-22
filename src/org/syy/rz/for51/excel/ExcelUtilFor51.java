package org.syy.rz.for51.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.syy.rz.for51.entity.Resume;

import java.util.List;


public class ExcelUtilFor51 {

    /**
     * 导出所有简历
     * @param list  简历列表
     * @return      工作簿
     */
    public static HSSFWorkbook exportTotalResume(List<Resume> list) { // 创建excel文件对象

        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第二行
        Row row = sheet.createRow(0);
        // 文件头字体
        Font font = createFonts(wb, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 200);

        // 给第二行添加文本
        createCell(wb, row, 0, "匹配度", font);
        createCell(wb, row, 1, "姓名", font);
        createCell(wb, row, 2, "职位名称", font);
        createCell(wb, row, 3, "公司/部门", font);
        createCell(wb, row, 4, "工作地点", font);
        createCell(wb, row, 5, "年龄", font);
        createCell(wb, row, 6, "学历", font);
        createCell(wb, row, 7, "工作年限", font);
        createCell(wb, row, 8, "投递时间", font);
        // 第二行表示
        int line = 1;
        // 这里将学员的信心存入到表格中
        for (Resume rz : list) {
            // 创建一行
            Row rowData = sheet.createRow(line++);
            createCell(wb, rowData, 0, rz.getMatchDegree(), font);
            createCell(wb, rowData, 1, rz.getName(), font);
            createCell(wb, rowData, 2, rz.getPosition(), font);
            createCell(wb, rowData, 3, rz.getCompany(), font);
            createCell(wb, rowData, 4, rz.getWorkPlace(), font);
            createCell(wb, rowData, 5, rz.getAge(), font);
            createCell(wb, rowData, 6, rz.getEducation(), font);
            createCell(wb, rowData, 7, rz.getWorkYears(), font);
            createCell(wb, rowData, 8, rz.getSendTime(), font);
        }
        return wb;
    }

    /**
     * 创建单元格并设置样式,值

     */
    public static void createCell(Workbook wb, Row row, int column, String value, Font font) {

        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        cellStyle.setFont(font);
        cell.setCellStyle(cellStyle);
    }

    /**
     * 设置字体

     */
    public static Font createFonts(Workbook wb, short bold, String fontName, boolean isItalic,
            short hight) {

        Font font = wb.createFont();
        font.setFontName(fontName);
        font.setBoldweight(bold);
        font.setItalic(isItalic);
        font.setFontHeight(hight);
        return font;
    }

}
