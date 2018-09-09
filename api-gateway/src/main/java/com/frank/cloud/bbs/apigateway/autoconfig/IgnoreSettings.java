package com.frank.cloud.bbs.apigateway.autoconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Created by  Frank on 2018-1-10.
 */
@Configuration
@ConfigurationProperties(prefix="ignore")
public class IgnoreSettings {
    List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
