package com.sah.shardingSphere.message.enums;


import java.util.Objects;

/**
 * 发送消息类型枚举
 */
public enum SendMsgTypeEnum {

    //推送方式：1短信 2邮件 3微信
    SMS(1, "短信"),
    EMAIL(2, "邮件"),
    WX(3, "微信");

    private Integer type;

    private String implClass;

    private SendMsgTypeEnum(Integer type, String implClass) {
        this.type = type;
        this.implClass = implClass;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }

    public static SendMsgTypeEnum getByType(Integer type) {
        if (Objects.isNull(type)) {
            return null;
        }
        for (SendMsgTypeEnum val : values()) {
            if (val.getType().equals(type)) {
                return val;
            }
        }
        return null;
    }
}
