package com.sah.shardingSphere.security;

import com.alibaba.fastjson.JSON;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.common.ResultCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class ResourceAuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        CommonResponse result= CommonResponse.error(ResultCode.USER_NOT_LOGIN.getCode(),
                ResultCode.USER_NOT_LOGIN.getMessage());
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().write(JSON.toJSONString(result));
    }
}
