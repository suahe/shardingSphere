package com.sah.shardingSphere.mybatis.hander;

import com.sah.shardingSphere.enums.EnumCode;
import com.sah.shardingSphere.util.EnumUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author linpx
 * @date 2022/5/5 上午 11:07
 */
public class MybatisEnumTypeHandler<E extends Enum<E> & EnumCode> extends BaseTypeHandler<E> {
    private final Class<E> enumClassType;

    public MybatisEnumTypeHandler(Class<E> enumClassType) {
        this.enumClassType = enumClassType;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E e, JdbcType jdbcType) throws SQLException {
        if (jdbcType == null) {
            ps.setString(i, e.getCode().toString());
        } else {
            ps.setObject(i, e.getCode(), jdbcType.TYPE_CODE);
        }
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object s = rs.getObject(columnName);
        return s == null ? null : EnumUtil.valueOfCode(this.enumClassType, s);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object s = rs.getObject(columnIndex);
        return s == null ? null : EnumUtil.valueOfCode(this.enumClassType, s);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object s = cs.getObject(columnIndex);
        return s == null ? null : EnumUtil.valueOfCode(this.enumClassType, s);
    }
}
