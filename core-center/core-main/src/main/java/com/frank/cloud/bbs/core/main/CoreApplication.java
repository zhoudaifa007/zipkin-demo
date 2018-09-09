package com.frank.cloud.bbs.core.main;

import javax.servlet.MultipartConfigElement;

import com.frank.cloud.bbs.core.main.filter.LogFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication(scanBasePackages = {"com.frank.cloud.bbs.core"})
@MapperScan(basePackages = "com.frank.cloud.bbs.core.service.dao")
@ImportResource(locations={"classpath:application-bean.xml"})
@EnableHystrix
@EnableFeignClients(basePackages = {"com.frank.cloud.bbs.core.common"})
@EnableAsync
@EnableDiscoveryClient
public class CoreApplication {

	private  static  final Logger LOGGER = LoggerFactory.getLogger(CoreApplication.class);

	private static final String CHARACTER_ENCODEING = "UTF-8";
	
    public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
        LOGGER.info("Core Service Start Success!!!");
    }

    @Bean
	protected FilterRegistrationBean registerCharacterEncodingFilter(@Value("${access.urls}") String urls) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		// 增加UTF-8编码过滤器
		CharacterEncodingFilter chataterEncodeFilter = new CharacterEncodingFilter();
		chataterEncodeFilter.setEncoding(CHARACTER_ENCODEING);
		chataterEncodeFilter.setForceEncoding(true);
		registration.setAsyncSupported(true);
		registration.setFilter(chataterEncodeFilter);
		registration.addUrlPatterns(urls);
		return registration;
	}

//	@Bean
//	protected FilterRegistrationBean registerThirdRequestHandlerProFilter(@Value("${access.urls}") String filterUrls) {
//		FilterRegistrationBean registration = new FilterRegistrationBean();
//		AccessControlFilter accessControlFilter = new AccessControlFilter();
//		registration.setAsyncSupported(true);
//		registration.setFilter(accessControlFilter);
//		registration.addUrlPatterns(filterUrls);
//		return registration;
//	}

	@Bean
	protected FilterRegistrationBean registerThirdRequestHandlerProFilter(@Value("${access.urls}") String filterUrls) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		LogFilter logFilter = new LogFilter();
		registration.setAsyncSupported(true);
		registration.setFilter(logFilter);
		registration.addUrlPatterns(filterUrls);
		return registration;
	}

	@Bean
	MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setLocation("/upload/tmp");
		return factory.createMultipartConfig();
	}
}
