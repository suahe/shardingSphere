package com.sah.shardingSphere.controller;

import com.alibaba.excel.EasyExcel;
import com.sah.shardingSphere.excel.ExcelImportListener;
import com.sah.shardingSphere.excel.ExcelUtil;
import com.sah.shardingSphere.excel.dto.DemoData;
import com.sah.shardingSphere.security.NotAuthentication;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author suahe
 * @date 2023/5/18 14:08
 */
@RequestMapping("/excel")
@RestController
public class ExcelController {

    @GetMapping("/import")
    @NotAuthentication
    public void validateExcel(@RequestParam("file") MultipartFile file, HttpServletResponse response) throws IOException {
        InputStream inputStream = file.getInputStream();
        Workbook workbook = new XSSFWorkbook(inputStream);
        ExcelImportListener listener = new ExcelImportListener(workbook);
        //读取Excel数据
        EasyExcel.read(file.getInputStream(), DemoData.class, listener).sheet().doRead();
        ExcelUtil.export("error.xlsx", workbook, response);
    }

}
