package com.client.configuration;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@Configuration
public class FeignConfig {
    @Bean
    public BasicAuthRequestInterceptor mBasicAuthRequestInterceptor(){

        return  new BasicAuthRequestInterceptor("mohamed", "04101972");
    }

}
