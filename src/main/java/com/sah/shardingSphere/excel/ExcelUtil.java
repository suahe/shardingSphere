package com.sah.shardingSphere.excel;


import com.alibaba.excel.EasyExcel;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.sah.shardingSphere.excel.dto.DemoData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecgframework.poi.excel.entity.enmus.ExcelType;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;

/**
 * @author suahe
 * @date 2023/5/19 11:20
 */
public class ExcelUtil {

    /**
     * 将Workbook导出成Base64编码的字符串，输出给前端
     *
     * @param workbook Workbook对象
     * @return Base64编码的字符串
     * @throws IOException IO异常
     */
    private static String workbookToBase64(Workbook workbook) throws IOException {
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        workbook.write(byteArrayOut);
        byte[] bytes = byteArrayOut.toByteArray();
        // 对字节数组进行Base64编码
        String base64String = Base64.getEncoder().encodeToString(bytes);
        // 返回Base64编码的字符串，可以在前端进行解码，生成Excel文件
        return base64String;
    }

    /**
     * 调用exportWorkbook方法导出Workbook对象，并将Base64编码的字符串输出给前端
     *
     * @param fileName   文件名
     * @param workbook   Workbook对象
     * @param response 响应对象
     * @throws IOException IO异常
     */
    public static void export(String fileName, Workbook workbook, HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        // 将Workbook导出成Base64编码的字符串
        String base64String = workbookToBase64(workbook);
        // 将Base64编码的字符串输出给前端
        PrintWriter out = response.getWriter();
        out.write(base64String);
        out.flush();
        out.close();
    }

    public static void writeWorkbookToFile(Workbook workbook, String filePath) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }


    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("D://test.xlsx");
        Workbook workbook = WorkbookFactory.create(inputStream, ExcelType.XSSF.name());
        ExcelImportListener listener = new ExcelImportListener(workbook);
        //读取Excel数据
        EasyExcel.read("D://test.xlsx", DemoData.class, listener).sheet().headRowNumber(1).doRead();
        writeWorkbookToFile(workbook, "D://error.xlsx");
    }
}
