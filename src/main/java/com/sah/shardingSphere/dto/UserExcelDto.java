package com.sah.shardingSphere.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author suahe
 * @date 2023/5/31 10:37
 */
@Data
public class UserExcelDto {

    @ExcelProperty(value = "名称", index = 0)
    private String username;

    @ExcelProperty(value = "手机号", index = 1)
    private String telephone;

    @ExcelProperty(value = "邮件", index = 2)
    private String mail;
}
