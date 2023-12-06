package com.sah.shardingSphere.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sah.shardingSphere.enums.EnumCode;
import com.sah.shardingSphere.util.EnumUtil;
import org.springframework.beans.BeanUtils;

import java.io.IOException;

/**
 * 枚举序列化转换器
 *
 * @author linpx
 */
public class EnumCodeDeserializer extends JsonDeserializer<EnumCode> {

    @Override
    public EnumCode deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String value = jsonParser.getValueAsString();
        String currentName = jsonParser.currentName();
        Object currentValue = jsonParser.getCurrentValue();
        @SuppressWarnings("rawtypes")
        Class findPropertyType = BeanUtils.findPropertyType(currentName, currentValue.getClass());
        return EnumUtil.valueOfCode(findPropertyType, value);
    }
}