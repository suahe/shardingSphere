package com.sah.shardingSphere.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sah.shardingSphere.aspect.annotation.Dict;
import com.sah.shardingSphere.serializer.Bit;
import com.sah.shardingSphere.serializer.PrivacyEncrypt;
import com.sah.shardingSphere.serializer.PrivacyTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author suahe
 * @date 2022/9/21
 * @ApiNote
 */
@Data
@ApiModel("用户实体")
@TableName("sys_user")
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private String id;

    @NotNull
    @Excel(name = "账号", width = 20, orderNum = "0")
    @ApiModelProperty("账号")
    private String username;

    @Pattern(regexp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$", message = "手机号格式有误")
    @PrivacyEncrypt(type = PrivacyTypeEnum.PHONE)
    @Excel(name = "手机号", width = 20, orderNum = "1")
    @ApiModelProperty("手机号")
    private String telephone;

    @Email
    @PrivacyEncrypt(type = PrivacyTypeEnum.EMAIL)
    @Excel(name = "邮件", width = 20, orderNum = "2")
    @ApiModelProperty("邮件")
    private String mail;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("机构ID")
    private String depId;

    @Range(min = 0, max = 2, message = "用户状态有误")
    @Dict(dicCode = "user_status")
    @ApiModelProperty("用户状态")
    private String status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty("更新人")
    private String updateBy;

    @Bit(digit = 3)
    @TableField(exist = false)
    @ApiModelProperty("double位数测试字段")
    private Double num;
}
