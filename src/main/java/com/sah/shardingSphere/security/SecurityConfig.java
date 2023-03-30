package com.sah.shardingSphere.security;

import com.sah.shardingSphere.security.hander.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author suahe
 * @date 2023/3/28
 * @ApiNote
 */
//Spring Security框架开启
@EnableWebSecurity
//授权全局配置
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private NotAuthenticationConfig notAuthenticationConfig;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //支持跨域
        http.cors().and()
                //csrf关闭
                .csrf().disable()
                //配置哪些需要认证 哪些不需要认证
                .authorizeRequests(rep -> rep.antMatchers(notAuthenticationConfig.getPermitAllUrls().toArray(new String[0]))
                        .permitAll().anyRequest().authenticated())
                .exceptionHandling()
                //认证异常处理
                .authenticationEntryPoint(new ResourceAuthExceptionEntryPoint())
                //授权异常处理
                .accessDeniedHandler(new CustomizeAccessDeniedHandler())
                //登录认证处理
                .and().formLogin()
                .successHandler(new CustomizeAuthenticationSuccessHandler())
                .failureHandler(new CustomizeAuthenticationFailureHandler())
                //登出
                .and().logout().permitAll().addLogoutHandler(new CustomizeLogoutHandler())
                .logoutSuccessHandler(new CustomizeLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                //自定义认证
                .and().userDetailsService(authUserService);
        return http.build();
    }


    /**
     * 获取AuthenticationManager
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * 密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean("ssc")
    public SecuritySecurityCheckService permissionService() {
        return new SecuritySecurityCheckService();
    }
}
