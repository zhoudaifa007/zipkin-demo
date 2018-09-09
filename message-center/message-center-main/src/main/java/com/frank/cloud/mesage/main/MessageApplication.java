package com.frank.cloud.mesage.main;

import com.frank.cloud.mesage.main.filter.AccessControlFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;

/**
 * Created by  Frank on 2017-12-18.
 */
@SpringBootApplication(scanBasePackages = { "com.frank.cloud.message" })
@MapperScan(basePackages = "com.frank.cloud.message.service.dao")
@ImportResource(locations = { "classpath:application-bean.xml" })
@EnableEurekaClient
@EnableFeignClients(basePackages = { "com.frank.cloud.mesage" })
public class MessageApplication {

	@Value("${server.port}")
	private String serverPort;
	
	@Value("${sysConfig.socketio.server.host}")
	private String socketHost;
	
	@Value("${sysConfig.socketio.server.port}")
	private Integer socketPort;

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageApplication.class);

	private static final String CHARACTER_ENCODEING = "UTF-8";

	public static void main(String[] args) {
		new SpringApplicationBuilder(MessageApplication.class).web(true).run(args);
		LOGGER.info("Sink Service Start Success!!!");
	}

	@Bean
	public SocketIOServer socketIOServer() {
		com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
		config.setHostname(socketHost);
		config.setPort(socketPort);
		final SocketIOServer server = new SocketIOServer(config);
		return server;
	}

	@Bean
	public SpringAnnotationScanner springAnnotationScanner(SocketIOServer socketServer) {
		return new SpringAnnotationScanner(socketServer);
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

	@Bean
	protected FilterRegistrationBean registerThirdRequestHandlerProFilter(@Value("${access.urls}") String filterUrls) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		AccessControlFilter accessControlFilter = new AccessControlFilter();
		registration.setAsyncSupported(true);
		registration.setFilter(accessControlFilter);
		registration.addUrlPatterns(filterUrls);
		return registration;
	}
}
