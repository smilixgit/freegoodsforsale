package com.example.yecaoshi.config;

import com.example.yecaoshi.interC.adminLoginInterceptor;
import com.example.yecaoshi.interC.customerLoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public customerLoginInterceptor getLoginInterceptor(){
        return new customerLoginInterceptor();
    }
    public adminLoginInterceptor getAdminLoginInterceptor(){ return new adminLoginInterceptor();}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {


    }
}
