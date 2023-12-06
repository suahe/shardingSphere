package com.sah.shardingSphere.util;

import com.sah.shardingSphere.enums.EnumCode;
import java.util.Arrays;

/**
 * @author linpx
 * @date 2022/5/4 下午 5:17
 */
public class EnumUtil {
    /**
     * 返回指定编码的枚举
     *
     * @param code
     * @return SharedObjTypeEnum
     * @throws
     */
    public static <T extends EnumCode> T valueOfCode(Class<T> clazz, Object code) {
        return valueOfCode(clazz, code, null);
    }

    /**
     * 返回带默认值的指定编码的枚举
     *
     * @param clazz
     * @param code
     * @param defaultVal
     * @param <T>
     * @return
     */
    public static <T extends EnumCode> T valueOfCode(Class<T> clazz, Object code, T defaultVal) {
        if (code != null) {
            return Arrays.stream(clazz.getEnumConstants()).filter((e) -> {
                if (!(e.getCode() instanceof String) && code instanceof String) {
                    return e.getCode().toString().equals(code);
                }
                return e.getCode().equals(code);
            }).findAny().orElse(defaultVal);
        }
        return defaultVal;
    }
}
