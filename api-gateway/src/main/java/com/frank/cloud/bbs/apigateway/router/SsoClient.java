package com.frank.cloud.bbs.apigateway.router;

import com.alibaba.fastjson.JSONObject;
import com.frank.cloud.bbs.apigateway.model.UserInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by  Frank on 2018-1-9.
 */
@Component
public class SsoClient {
    private static final Logger logger = LoggerFactory.getLogger(SsoClient.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${sso.host}")
    private String SSO_HOST;

    @Value("${sso.port}")
    private String SSO_PORT;

    private static final String CHECK_COOKIE = "/v1/sso/service/check/cookie";

    public UserInfoDto checkCookie(String cookie) {
        JSONObject json = new JSONObject();
        json.put("jsession", cookie);
        UserInfoDto userInfoDto = null;

        String jsonBody = JSONObject.toJSONString(json);
        JSONObject result = sendRequest(CHECK_COOKIE, jsonBody);
        if(result.getInteger("code") == 200) {
            JSONObject data = result.getJSONObject("data") ;
            if(data != null) {
                userInfoDto  = JSONObject.parseObject(data.toJSONString(),UserInfoDto.class);
            }
        }
        return userInfoDto;
    }

    private JSONObject sendRequest(String path, String jsonParams) {
        long startTime = System.currentTimeMillis();
        String sid = "22222";
        JSONObject result = null;
        String url = SSO_HOST + ":" + SSO_PORT + path;
        try {
            logger.info("sid={}, reqUrl={}, jsonParams={}", sid, url, jsonParams);
            String response = postJson(url, jsonParams);
            logger.info("sid={}, reqUrl={}, cost={}ms, resp={}", sid, url, System.currentTimeMillis() - startTime, response);
            result = JSONObject.parseObject(response);
        } catch (Exception e) {
        }
        return result;
    }

    private String postJson(String url,String requestJson) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
        String result = restTemplate.postForObject(url, entity, String.class);
        return result;
    }
}
