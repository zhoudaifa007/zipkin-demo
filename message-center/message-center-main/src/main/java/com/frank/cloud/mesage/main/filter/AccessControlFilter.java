package com.frank.cloud.mesage.main.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 
 * <pre>
 * Http请求过滤器。
 * </pre>
 *
 * @version 2.0.0
 * 
 *          <pre>
 * 修改记录
 * 修改后版本:         修改人：  
 * 修改日期:           修改内容:
 * </pre>
 */
public class AccessControlFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException 
	{
		//设置允许跨域
		response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET");
        //允许content-type跨域
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
		filterChain.doFilter(request, response);
	}
}
