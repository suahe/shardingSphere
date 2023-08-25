package com.sah.shardingSphere;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

@Slf4j
@EnableRetry
@EnableScheduling
@MapperScan("com.sah.shardingSphere.mapper")
@SpringBootApplication
public class ShardingSphereApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext application = SpringApplication.run(ShardingSphereApplication.class, args);
        Environment env = application.getEnvironment();
        String ip = InetAddress.getLocalHost().getHostAddress();
        String port = env.getProperty("server.port");
        String path = getContextPath(env);
        log.info("\n----------------------------------------------------------\n\t" +
                "Application Boot is running! Access URLs:\n\t" +
                "Local: \t\thttp://localhost:" + port + path + "\n\t" +
                "External: \thttp://" + ip + ":" + port + path + "\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "doc.html\n" +
                "----------------------------------------------------------");
    }

    private static String getContextPath(Environment env) {
        String path = env.getProperty("server.servlet.context-path");
        if (StringUtils.isEmpty(path)) {
            return "";
        }
        return path.trim();
    }

}
