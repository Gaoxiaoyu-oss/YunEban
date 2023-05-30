package com.gxy.server;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.gxy.server.mapper")
@EnableScheduling//开启定时任务
public class YebApplication {
    public static void main(String[] args) {
        SpringApplication.run(YebApplication.class,args);
    }
}
