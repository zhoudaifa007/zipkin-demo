package com.frank.cloud.registry.center;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class RegitryApplication {

    private static final Logger logger = LoggerFactory.getLogger(RegitryApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RegitryApplication.class, args);
        logger.info("Regitry Center Started Success!!");
    }

}
