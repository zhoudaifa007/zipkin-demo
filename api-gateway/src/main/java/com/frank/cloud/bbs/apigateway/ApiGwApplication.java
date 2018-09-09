package com.frank.cloud.bbs.apigateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.servlet.MultipartConfigElement;

@EnableZuulProxy
@SpringCloudApplication
public class ApiGwApplication {

	private static final Logger logger = LoggerFactory.getLogger(ApiGwApplication.class);

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(ApiGwApplication.class, args);
		logger.info("Api GateWay Started Success!!!");
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/upload/tmp");
		return factory.createMultipartConfig();
	}
}
