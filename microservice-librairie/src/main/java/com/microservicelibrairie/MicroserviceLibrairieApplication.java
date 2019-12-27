package com.microservicelibrairie;

import brave.sampler.Sampler;
import com.microservicelibrairie.dao.LibrairieRepository;
import com.microservicelibrairie.entities.Librairie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties
@EnableDiscoveryClient
@EnableScheduling
public class MicroserviceLibrairieApplication {
    @Autowired
    LibrairieRepository librairieRepository;

    public static void main(String[] args) {
        SpringApplication.run(MicroserviceLibrairieApplication.class, args);
    }
    @Bean
    public Sampler defaultSampler(){
        return Sampler.ALWAYS_SAMPLE;
    }





}
