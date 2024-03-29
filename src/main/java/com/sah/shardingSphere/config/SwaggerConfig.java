package com.sah.shardingSphere.config;

import com.sah.shardingSphere.common.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("com.sah.shardingSphere.controller")).paths(PathSelectors.any())
                .build().globalOperationParameters(setHeaderToken());

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("shardingSphere接口文档")
                .description("swagger实战")
                .termsOfServiceUrl("")
                //作者信息、联系方式：Contact(String name, String url, String email)
                .contact(new Contact("sah","","1054599027@qq.com"))
                .version("1.0.0").build();
    }

    /**
     * @Description: 设置swagger文档中全局参数
     * @param
     * @Date: 2020/9/11 10:15
     * @return: java.util.List<springfox.documentation.service.Parameter>
     */

    private List<Parameter> setHeaderToken() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder userId = new ParameterBuilder();
        userId.name(CommonConstant.X_ACCESS_TOKEN).description("用户TOKEN").modelRef(new ModelRef("string")).parameterType("header")
                .required(true).build();
        pars.add(userId.build());
        return pars;
    }
}
