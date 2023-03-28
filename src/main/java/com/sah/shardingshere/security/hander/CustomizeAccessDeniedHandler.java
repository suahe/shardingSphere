package com.sah.shardingshere.security.hander;

import com.alibaba.fastjson.JSON;
import com.sah.shardingshere.common.CommonResponse;
import com.sah.shardingshere.common.ResultCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResponse result = CommonResponse.error(ResultCode.NO_PERMISSION.getCode(),
                ResultCode.NO_PERMISSION.getMessage());
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
