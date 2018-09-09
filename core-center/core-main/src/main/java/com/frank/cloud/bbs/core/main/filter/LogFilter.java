package com.frank.cloud.bbs.core.main.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * Created by Frank on 2018/8/1.
 */


public class LogFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(request);
        MyResponseWrapper myResponseWrapper = new MyResponseWrapper(response);
        String req = myRequestWrapper.getBody();
        JSONObject requestParam = JSON.parseObject(req);
        String sid = UUID.randomUUID().toString().replace("-", "");
        long startTime = System.currentTimeMillis();
        logger.info("sid={},uri={},request===>{}",sid,uri,req);
        String responseContent = null;
        try {
            filterChain.doFilter(myRequestWrapper, myResponseWrapper);
            byte[] arr = myResponseWrapper.getCaptureAsBytes();
            response.getOutputStream().write(arr);
            responseContent = new String(arr, StandardCharsets.UTF_8);

        } finally {
            logger.info("sid={},uri={} cost={}ms,response===>{}",sid,uri,System.currentTimeMillis() - startTime, responseContent);

        }
    }

}