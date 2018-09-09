package com.frank.cloud.bbs.apigateway.filter;

import com.frank.cloud.bbs.apigateway.autoconfig.IgnoreSettings;
import com.frank.cloud.bbs.apigateway.model.UserInfoDto;
import com.frank.cloud.bbs.apigateway.router.SsoClient;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


/**
 * 鉴权控制
 */
@Component
public class AuthPreFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthPreFilter.class);

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();
        MyRequestWrapper myRequestWrapper = new MyRequestWrapper(request);
        logger.info("url={} ,request={}", uri, myRequestWrapper.getBody());
        return null;
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public int filterOrder() {
        return 0;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }
}