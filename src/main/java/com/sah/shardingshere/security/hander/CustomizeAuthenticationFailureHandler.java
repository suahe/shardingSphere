package com.sah.shardingshere.security.hander;

import com.alibaba.fastjson.JSON;
import com.sah.shardingshere.common.CommonResponse;
import com.sah.shardingshere.common.ResultCode;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        //返回json数据
        CommonResponse result = null;
        if (exception instanceof AccountExpiredException) {
            //账号过期
            result = CommonResponse.error(ResultCode.USER_ACCOUNT_EXPIRED.getCode(), ResultCode.USER_ACCOUNT_EXPIRED.getMessage());
        } else if (exception instanceof BadCredentialsException) {
            //密码错误
            result = CommonResponse.error(ResultCode.USER_CREDENTIALS_ERROR.getCode(), ResultCode.USER_CREDENTIALS_ERROR.getMessage());
//        } else if (exception instanceof CredentialsExpiredException) {
//            //密码过期
//            result = CommonResponse.error(ResultCode.USER_CREDENTIALS_EXPIRED);
//        } else if (exception instanceof DisabledException) {
//            //账号不可用
//            result = CommonResponse.error(ResultCode.USER_ACCOUNT_DISABLE);
//        } else if (exception instanceof LockedException) {
//            //账号锁定
//            result = CommonResponse.error(ResultCode.USER_ACCOUNT_LOCKED);
//        } else if (exception instanceof InternalAuthenticationServiceException) {
//            //用户不存在
//            result = CommonResponse.error(ResultCode.USER_ACCOUNT_NOT_EXIST);
        } else {
            //其他错误
            result = CommonResponse.error(ResultCode.COMMON_FAIL.getCode(), ResultCode.COMMON_FAIL.getMessage());
        }
        //处理编码方式，防止中文乱码的情况
        response.setContentType("text/json;charset=utf-8");
        //塞到HttpServletResponse中返回给前台
        response.getWriter().write(JSON.toJSONString(result));
    }
}
