package com.sah.shardingSphere.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class BitSerializer extends JsonSerializer<Object> implements ContextualSerializer {

    private Integer digit;

    @Override
    public void serialize(Object origin, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        log.info("BitSerializer serialize origin:{}, digit:{}", origin, digit);
        DecimalFormat df = new DecimalFormat(getBitFm(digit));
        Double d = Double.parseDouble(origin.toString());
        jsonGenerator.writeString(df.format(d));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty) throws JsonMappingException {
        if (beanProperty != null) {
            if (Objects.equals(beanProperty.getType().getRawClass(), Double.class) || Objects.equals(beanProperty.getType().getRawClass(), Float.class)) {
                Bit bit = beanProperty.getAnnotation(Bit.class);
                if (bit == null) {
                    bit = beanProperty.getContextAnnotation(Bit.class);
                }
                if (bit != null) {
                    return new BitSerializer(bit.digit());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

    //获取位数格式
    public static String getBitFm(int num) {
        if (num <= 0) {
            return "0";
        }
        StringBuilder stringBuilder = new StringBuilder("0.");
        for (int i = 0; i < num; i++) {
            stringBuilder.append("#");
        }
        return stringBuilder.toString();
    }
}
