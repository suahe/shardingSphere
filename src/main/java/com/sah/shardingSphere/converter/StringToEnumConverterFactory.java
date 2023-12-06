package com.sah.shardingSphere.converter;

import com.sah.shardingSphere.enums.EnumCode;
import com.sah.shardingSphere.util.EnumUtil;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author linpx
 * @date 2022/5/4 下午 3:23
 */
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum> {
    /**
     * 枚举Code转成枚举转换器
     * @param <T>
     */
    private static class CodeToEnumConverter<T extends EnumCode> implements Converter<String, T> {
        private Class<T> enumType;

        public CodeToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (EnumCode.class.isAssignableFrom(this.enumType)) {
                return EnumUtil.valueOfCode(this.enumType, source.trim());
            }
            return null;
        }
    }

    /**
     * 字符串转枚举转换器
     * @param <T>
     */
    private static class StringToEnumConverter<T extends Enum> implements Converter<String, T> {
        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> targetType) {
        if (EnumCode.class.isAssignableFrom(targetType)) {
            return new CodeToEnumConverter(targetType);
        } else {
            return new StringToEnumConverter(targetType);
        }
    }
}
