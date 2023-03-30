package com.sah.shardingSphere.aspect;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.sah.shardingSphere.aspect.annotation.AutoLog;
import com.sah.shardingSphere.common.CommonConstant;
import com.sah.shardingSphere.common.CommonResponse;
import com.sah.shardingSphere.entity.SysLog;
import com.sah.shardingSphere.enums.ModuleType;
import com.sah.shardingSphere.security.model.LoginDTO;
import com.sah.shardingSphere.service.ISysLogService;
import com.sah.shardingSphere.util.IPUtil;
import com.sah.shardingSphere.util.JwtUtil;
import com.sah.shardingSphere.util.SpringContextUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.parsing.GenericTokenParser;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 系统日志，切面处理类
 *
 * @Author scott
 * @email jeecgos@163.com
 * @Date 2018年1月14日
 */
@Slf4j
@Aspect
@Component
public class AutoLogAspect {

    private Map<Method, Map<String, LogContent>> map = new ConcurrentHashMap<>();

    @Resource
    private ISysLogService sysLogService;

    @Pointcut("@annotation(com.sah.shardingSphere.aspect.annotation.AutoLog)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = null;
        Object responseRes = null;
        try {
            //执行方法
            result = point.proceed();
            responseRes = result;
        } catch (Exception e) {
            log.error("method execute error:{}", e.getMessage());
            responseRes = getStackTraceMessage(e);
            throw e;
        } finally {
            //执行时长(毫秒)
            long time = System.currentTimeMillis() - beginTime;

            //保存日志
            saveSysLog(point, time, responseRes);
        }
        return result;
    }

    private void saveSysLog(ProceedingJoinPoint joinPoint, long time, Object result) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog dto = new SysLog();
        AutoLog syslog = method.getAnnotation(AutoLog.class);
        if (syslog != null) {
            //update-begin-author:taoyan date:
            String content = syslog.value();
            if (syslog.module() == ModuleType.ONLINE) {
                content = getOnlineLogContent(result, content);
            } else {
                content = parseLogContent(method, joinPoint.getArgs(), syslog);
            }
            //注解上的描述,操作日志内容
            dto.setLogType(syslog.logType());
            dto.setLogContent(content);
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        dto.setMethod(className + "." + methodName + "()");


        //设置操作类型
        if (dto.getLogType() == CommonConstant.LOG_TYPE_2 && syslog != null) {
            dto.setOperateType(getOperateType(methodName, syslog.operateType()));
        }

        //获取request
        HttpServletRequest request = SpringContextUtil.getHttpServletRequest();
        //请求的参数
        dto.setRequestParam(getRequestParams(request, joinPoint));
        //URL
        String requestURI = request.getRequestURI();
        dto.setRequestUrl(requestURI);
        //请求方式
        String requestType = request.getMethod();
        dto.setRequestType(requestType);
        //响应的结果
        if (result != null) {
            String responseRes = JSONObject.toJSONString(result);
            responseRes = responseRes.length() > 1000 ? (responseRes.substring(0, 995) + "......") : responseRes;
            dto.setResponseRes(responseRes);
        }
        //设置IP地址
        dto.setIp(IPUtil.getIpAddr(request));
        //获取登录用户信息
        LoginDTO loginDTO = JwtUtil.getLoginUserByToken();
        if (loginDTO != null) {
            dto.setUserId(loginDTO.getUserId());
            dto.setUsername(loginDTO.getUsername());
        }
        //耗时
        dto.setCostTime(time);
        dto.setCreateTime(new Date());
        //保存系统日志
        sysLogService.save(dto);
    }


    /**
     * 获取操作类型
     */
    private int getOperateType(String methodName, int operateType) {
        if (operateType > 0) {
            return operateType;
        }
        if (methodName.startsWith("list")) {
            return CommonConstant.OPERATE_TYPE_1;
        }
        if (methodName.startsWith("add")) {
            return CommonConstant.OPERATE_TYPE_2;
        }
        if (methodName.startsWith("edit")) {
            return CommonConstant.OPERATE_TYPE_3;
        }
        if (methodName.startsWith("delete")) {
            return CommonConstant.OPERATE_TYPE_4;
        }
        if (methodName.startsWith("import")) {
            return CommonConstant.OPERATE_TYPE_5;
        }
        if (methodName.startsWith("export")) {
            return CommonConstant.OPERATE_TYPE_6;
        }
        return CommonConstant.OPERATE_TYPE_1;
    }

    /**
     * @param request:   request
     * @param joinPoint: joinPoint
     * @Description: 获取请求参数
     * @author: scott
     * @date: 2020/4/16 0:10
     * @Return: java.lang.String
     */
    private String getRequestParams(HttpServletRequest request, JoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        String params = "";
        if ("POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod)) {
            Object[] paramsArray = joinPoint.getArgs();
            // java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
            //  https://my.oschina.net/mengzhang6/blog/2395893
            Object[] arguments = new Object[paramsArray.length];
            for (int i = 0; i < paramsArray.length; i++) {
                if (paramsArray[i] instanceof BindingResult || paramsArray[i] instanceof ServletRequest || paramsArray[i] instanceof ServletResponse || paramsArray[i] instanceof MultipartFile) {
                    //ServletRequest不能序列化，从入参里排除，否则报异常：java.lang.IllegalStateException: It is illegal to call this method if the current request is not in asynchronous mode (i.e. isAsyncStarted() returns false)
                    //ServletResponse不能序列化 从入参里排除，否则报异常：java.lang.IllegalStateException: getOutputStream() has already been called for this response
                    continue;
                }
                arguments[i] = paramsArray[i];
            }
            //update-begin-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
            PropertyFilter profilter = new PropertyFilter() {
                @Override
                public boolean apply(Object o, String name, Object value) {
                    if (value != null && value.toString().length() > 500) {
                        return false;
                    }
                    return true;
                }
            };
            params = JSONObject.toJSONString(arguments, profilter);
            //update-end-author:taoyan date:20200724 for:日志数据太长的直接过滤掉
        } else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params += "  " + paramNames[i] + ": " + args[i];
                }
            }
        }
        return params;
    }

    /**
     * online日志内容拼接
     *
     * @param obj
     * @param content
     * @return
     */
    private String getOnlineLogContent(Object obj, String content) {
        if (CommonResponse.class.isInstance(obj)) {
            CommonResponse res = (CommonResponse) obj;
            String onlineLogContent = res.getLogContent();
            if (StringUtils.isNotEmpty(onlineLogContent)) {
                return onlineLogContent;
            } else {
                return content;
            }
        }
        return content;
    }

    private String parseLogContent(Method method, Object[] args, AutoLog syslog) {
        GenericTokenParser genericTokenParser = new GenericTokenParser("${", "}", fillContent -> {
            Map<String, LogContent> WaterLogParserMap = map.computeIfAbsent(method, key -> new ConcurrentHashMap<>());
            LogContent logContent = WaterLogParserMap.computeIfAbsent(fillContent, key2 -> resolveOther(method, key2));
            int paramIndex = logContent.getParamIndex();
            Object param = args[paramIndex];
            if (param == null) {
                if (syslog.operateType() == CommonConstant.OPERATE_TYPE_1) {
                    return "";
                }
                return "未输入值";
            }
            if (logContent.isPrimaryType()) {
                return String.valueOf(param);
            } else {
                MetaObject metaObject = SystemMetaObject.forObject(param);
                Object obj = metaObject.getValue(logContent.getNewFillContent());
                if (obj == null) {
                    if (syslog.operateType() == CommonConstant.OPERATE_TYPE_1) {
                        return "";
                    }
                    return "未输入值";
                }
                return String.valueOf(obj);
            }
        });
        //解析${}中的值
        String content = genericTokenParser.parse(syslog.value());
        return content;
    }

    private LogContent resolveOther(Method method, String fillContent) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        String[] paramNames = u.getParameterNames(method);
        int index = fillContent.indexOf(".");
        LogContent content = new LogContent();
        if (index < 0) {
            content.setPrimaryType(true);
            for (int i = 0; i < paramNames.length; i++) {
                if (paramNames[i].equals(fillContent)) {
                    content.setParamIndex(i);
                    break;
                }
            }
        } else {
            content.setPrimaryType(false);
            String paramName = fillContent.substring(0, index);
            String field = fillContent.substring(index + 1);
            content.setNewFillContent(field);
            for (int i = 0; i < paramNames.length; i++) {
                if (paramNames[i].equals(paramName)) {
                    content.setParamIndex(i);
                    break;
                }
            }
        }
        return content;
    }

    private static String getStackTraceMessage(Exception e) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw);){
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return null;
        }
    }

    @Getter
    @Setter
    static class LogContent {
        /**
         * 参数下标
         */
        private int paramIndex;
        /**
         * 是否是基本类型和字符串，这里表现为是否包含.
         */
        private boolean isPrimaryType;

        private String newFillContent;
    }
}
