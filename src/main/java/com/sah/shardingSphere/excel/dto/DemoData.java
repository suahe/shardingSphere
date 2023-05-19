package com.sah.shardingSphere.excel.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author suahe
 * @date 2023/5/18 12:07
 */
@Data
public class DemoData {

    @ExcelProperty("姓名")
    @NotBlank(message = "姓名不能为空")
    private String name;

    @ExcelProperty("年龄")
    @Min(value = 1, message = "年龄必须大于等于 1")
    @Max(value = 150, message = "年龄必须小于等于 150")
    private Integer age;
}
