package com.example.yecaoshi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication

@MapperScan("com.example.yecaoshi.mapper")
public class YecaoshiApplication {


    public static void main(String[] args) {
        SpringApplication.run(YecaoshiApplication.class, args);
    }
    //纯纯测试用的哈
}
