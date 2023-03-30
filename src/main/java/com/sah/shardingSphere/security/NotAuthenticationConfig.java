package com.sah.shardingSphere.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Service
public class NotAuthenticationConfig implements InitializingBean, ApplicationContextAware {

    private static final String PATTERN = "\\{(.*?)}";

    public static final String ASTERISK = "*";


    private ApplicationContext applicationContext;

    @Getter
    @Setter
    private List<String> permitAllUrls = new ArrayList<String>() {
        {
            add("/");
            add("/doc.html");
            add("/**/*.js");
            add("/**/*.css");
            add("/**/*.html");
            add("/**/*.svg");
            add("/**/*.pdf");
            add("/**/*.jpg");
            add("/**/*.png");
            add("/**/*.ico");
            add("/**/*.ttf");
            add("/**/*.woff");
            add("/**/*.woff2");
            add("/druid/**");
            add("/swagger-ui.html");
            add("/swagger**/**");
            add("/webjars/**");
            add("/v2/**");
            add("/**/*.js.map");
            add("/**/*.css.map");
            add("/backstage/**");
        }
    };

    @PostMapping
    public void loadPermitUrl() {

    }

    @Override
    public void afterPropertiesSet() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
        map.keySet().forEach(x -> {
            HandlerMethod handlerMethod = map.get(x);

            // 获取方法上边的注解 替代path variable 为 *
            NotAuthentication method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), NotAuthentication.class);
            Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(x.getPatternValues())
                    .forEach(url -> permitAllUrls.add(url.replaceAll(PATTERN, ASTERISK))));

            // 获取类上边的注解, 替代path variable 为 *
            NotAuthentication controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), NotAuthentication.class);
            Optional.ofNullable(controller).ifPresent(inner -> Objects.requireNonNull(x.getPatternValues())
                    .forEach(url -> permitAllUrls.add(url.replaceAll(PATTERN, ASTERISK))));
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
