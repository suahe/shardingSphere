package com.sah.shardingSphere.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@ApiModel("响应实体")
public class CommonResponse<T> implements Serializable {

    /**
     * 提示信息
     */
    @ApiModelProperty("提示信息")
    private String message;

    /**
     * 返回数据
     */
    @ApiModelProperty("返回数据")
    private T data;

    /**
     * 状态码
     */
    @ApiModelProperty("状态码")
    private Integer code;

    /**
     * 错误明细
     */
    @ApiModelProperty("详细信息")
    private String detailMessage;

    @JsonIgnore
    private String logContent;


    /**
     * 成功
     *
     * @param <T> 泛型
     * @return 返回结果
     */
    public static <T> CommonResponse<T> ok() {
        return ok(null);
    }

    /**
     * 成功
     *
     * @param data 传入的对象
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> CommonResponse<T> ok(T data) {
        CommonResponse<T> response = new CommonResponse<T>();
        response.code = ResultCode.SUCCESS.getCode();
        response.data = data;
        response.message = "返回成功";
        return response;
    }

    /**
     * 错误
     *
     * @param message 自定义返回信息
     * @param <T>     泛型
     * @return 返回信息
     */
    public static <T> CommonResponse<T> error(String message) {
        return error(ResultCode.COMMON_FAIL.getCode(), message, null);
    }

    /**
     * 错误
     *
     * @param code    自定义code
     * @param message 自定义返回信息
     * @param <T>     泛型
     * @return 返回信息
     */
    public static <T> CommonResponse<T> error(Integer code, String message) {
        return error(code, message, null);
    }

    /**
     * 错误
     *
     * @param code          自定义code
     * @param message       自定义返回信息
     * @param detailMessage 错误详情信息
     * @param <T>           泛型
     * @return 返回错误信息
     */
    public static <T> CommonResponse<T> error(Integer code, String message,
                                              String detailMessage) {
        CommonResponse<T> response = new CommonResponse<T>();
        response.code = code;
        response.data = null;
        response.message = message;
        response.detailMessage = detailMessage;
        return response;
    }

    public String getMessage() {
        return message;
    }

    public CommonResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public CommonResponse<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public CommonResponse<T> setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }

    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }
}
