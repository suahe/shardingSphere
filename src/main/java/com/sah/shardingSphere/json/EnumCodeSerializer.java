package com.sah.shardingSphere.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sah.shardingSphere.enums.EnumCode;

import java.io.IOException;

/**
 * 枚举序列化转换器
 *
 * @author linpx
 */
public class EnumCodeSerializer extends JsonSerializer<EnumCode> {
    @Override
    public void serialize(EnumCode enumCode, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (enumCode != null) {
            jsonGenerator.writeObject(enumCode.getCode());
        }
    }
}