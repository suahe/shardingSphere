package com.sah.shardingshere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sah.shardingshere.mapper")
@SpringBootApplication
public class ShardingshereDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingshereDemoApplication.class, args);
    }

}
