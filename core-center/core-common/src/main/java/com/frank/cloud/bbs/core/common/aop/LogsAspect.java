package com.frank.cloud.bbs.core.common.aop;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
public class LogsAspect {

	//切点
	@Pointcut("execution(* com.frank.cloud.bbs.core.api.*.*(..))")
	public void bbsLog() {}
	
	//环绕通知
	@Around("bbsLog()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		
		//获取当前调用的controller类的信息，便于生成log
		String classType = pjp.getTarget().getClass().getName();
		Class<?> clazz = Class.forName(classType);
		final Logger logger = LoggerFactory.getLogger(clazz);
		long startTime = System.currentTimeMillis();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        String userId = request.getHeader("userId");
        String uri = request.getRequestURI();
        Object[] args=pjp.getArgs();
        int len = args.length;
        if(len > 2) {
            logger.info("userId={}, uri={}, request===>{}", userId, uri, JSONObject.toJSON(args[len-1]));
        }else {
            logger.info("userId={}, uri={}, request===>{}", userId, uri);
        }
        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        logger.info("uri={}, cost={}ms, response={}", uri, System.currentTimeMillis()-startTime, JSONObject.toJSONString(result));
        return result;
    }
}
