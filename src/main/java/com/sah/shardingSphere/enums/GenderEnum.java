package com.sah.shardingSphere.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author suahe
 * @date 2023/12/6 16:22
 */
@Getter
@AllArgsConstructor
public enum GenderEnum implements EnumCode {

    UNKNOWN("0", "未知"),
    MALE("1", "男"),
    FEMALE("2", "女");

    @JsonValue
    private String code;

    private String desc;
}