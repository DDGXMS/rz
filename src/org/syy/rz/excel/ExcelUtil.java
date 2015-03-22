package org.syy.rz.excel;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.syy.rz.entity.Rz;

public class ExcelUtil {

    public static HSSFWorkbook exportExcelForStudent(List<Rz> list) { // 创建excel文件对象

        HSSFWorkbook wb = new HSSFWorkbook();
        // 创建一个张表
        Sheet sheet = wb.createSheet();
        // 创建第一行
        Row row = sheet.createRow(0);
        // 创建第二行
        Row row1 = sheet.createRow(1);
        // 文件头字体
        Font font0 = createFonts(wb, Font.BOLDWEIGHT_BOLD, "宋体", false, (short) 200);
        Font font1 = createFonts(wb, Font.BOLDWEIGHT_NORMAL, "宋体", false, (short) 200);
        // 合并第一行的单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
        // 设置第一列的文字
        createCell(wb, row, 0, "总数", font0);
        // 合并第一行的2列以后到8列（不包含第二列）
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 8));
        // 设置第二列的文字
        createCell(wb, row, 2, "基本信息", font0);
        // 给第二行添加文本
        createCell(wb, row1, 0, "匹配度", font1);
        createCell(wb, row1, 1, "姓名", font1);
        createCell(wb, row1, 2, "职位名称", font1);
        createCell(wb, row1, 3, "公司/部门", font1);
        createCell(wb, row1, 4, "工作地点", font1);
        createCell(wb, row1, 5, "年龄", font1);
        createCell(wb, row1, 6, "学历", font1);
        createCell(wb, row1, 7, "工作年限", font1);
        createCell(wb, row1, 8, "投递时间", font1);
        // 第三行表示
        int l = 2;
        // 这里将学员的信心存入到表格中
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            Row rowData = sheet.createRow(l++);
            Rz rz = list.get(i);
            createCell(wb, rowData, 0, rz.getPipei(), font1);
            createCell(wb, rowData, 1, rz.getName(), font1);
            createCell(wb, rowData, 2, rz.getPostion(), font1);
            createCell(wb, rowData, 3, rz.getCompany(), font1);
            createCell(wb, rowData, 4, rz.getWorkPlace(), font1);
            createCell(wb, rowData, 5, rz.getAge(), font1);
            createCell(wb, rowData, 6, rz.getEducation(), font1);
            createCell(wb, rowData, 7, rz.getWorkYears(), font1);
            createCell(wb, rowData, 8, rz.getSendTime(), font1);
        }
        return wb;
    }

    /**
     * 创建单元格并设置样式,值
     * 
     * @param wb
     * @param row
     * @param column
     * @param
     * @param
     * @param value
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
     * 
     * @param wb
     * @return
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

    /**
     * 判断是否为数字
     * 
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {

        if (str == null || "".equals(str.trim()) || str.length() > 10)
            return false;
        Pattern pattern = Pattern.compile("^0|[1-9]\\d*(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

}
