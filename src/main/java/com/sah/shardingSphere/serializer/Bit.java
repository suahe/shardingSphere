package com.sah.shardingSphere.serializer;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 位数处理
 */
@Target(ElementType.FIELD) // 作用在字段上
@Retention(RetentionPolicy.RUNTIME) // class文件中保留，运行时也保留，能通过反射读取到
@JacksonAnnotationsInside // 表示自定义自己的注解PrivacyEncrypt
@JsonSerialize(using = BitSerializer.class) // 该注解使用序列化的方式
public @interface Bit {

    /**
     * 小数点后位数
     */
    int digit() default 2;

}
