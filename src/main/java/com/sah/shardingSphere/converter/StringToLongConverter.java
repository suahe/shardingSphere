package com.sah.shardingSphere.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * @author sundeyao
 * @date 2021/10/19 18:07
 */
public class StringToLongConverter implements Converter<String, Long> {

    @Override
    public Long convert(String source) {
        source = source.replaceAll("\"", "");
        return Long.parseLong(source);
    }
}
