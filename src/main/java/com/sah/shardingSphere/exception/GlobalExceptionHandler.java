package com.sah.shardingSphere.exception;

import com.sah.shardingSphere.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 异常处理器
 *
 * @Author scott
 * @Date 2019
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(BusinessException.class)
    public CommonResponse<?> handleRRException(BusinessException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(e.getMessage());
    }

    /**
     * 处理登陆异常
     */
    @ExceptionHandler(LoginException.class)
    public CommonResponse<?> handleAppLoginException(LoginException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(e.getMessage());
    }

    /**
     * 处理redis异常
     */
    @ExceptionHandler(RedisException.class)
    public CommonResponse<?> handlePoolException(RedisException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(e.getMessage());
    }

    /**
     * 处理锁异常
     */
    @ExceptionHandler(LockException.class)
    public CommonResponse<?> handlePoolException(LockException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(e.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResponse<?> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(404, "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonResponse<?> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error("数据库中已存在该记录");
    }

    @ExceptionHandler(Exception.class)
    public CommonResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error("操作失败，" + e.getMessage());
    }

    /**
     * @param e
     * @return
     * @Author 政辉
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<?> HttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        StringBuffer sb = new StringBuffer();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法，");
        sb.append("支持以下");
        String[] methods = e.getSupportedMethods();
        if (methods != null) {
            for (String str : methods) {
                sb.append(str);
                sb.append("、");
            }
        }
        log.error(sb.toString(), e);
        //return Result.error("没有权限，请联系管理员授权");
        return CommonResponse.error(405, sb.toString());
    }

    /**
     * spring默认上传大小100MB 超出大小捕获异常MaxUploadSizeExceededException
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public CommonResponse<?> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error("文件大小超出10MB限制, 请压缩或降低文件质量! ");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public CommonResponse<?> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error("字段太长,超出数据库字段的长度");
    }

    @ExceptionHandler(PoolException.class)
    public CommonResponse<?> handlePoolException(PoolException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error("连接池异常!");
    }
}
