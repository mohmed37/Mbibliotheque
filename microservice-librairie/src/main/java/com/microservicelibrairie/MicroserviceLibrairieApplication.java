package com.microservicelibrairie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableConfigurationProperties
public class MicroserviceLibrairieApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceLibrairieApplication.class, args);
    }

}
