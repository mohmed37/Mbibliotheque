package com.clientiu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.clientiu")
@EnableDiscoveryClient
public class ClientiuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientiuApplication.class, args);
    }

}
