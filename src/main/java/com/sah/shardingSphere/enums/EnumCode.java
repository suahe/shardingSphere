package com.sah.shardingSphere.enums;

/**
 * 枚举实现本接口可以通过EnumUtil.valueOfCode方法获取枚举
 *
 * @author linpx
 * @date 2022/5/4 下午 5:15
 */
public interface EnumCode<T> {
    /**
     * 枚举Code值
     *
     * @return
     */
    T getCode();
}
