package com.mdl.springai.mcp.client.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 响应拦截器
 * 用于统一处理响应相关的设置
 *
 * @author meidanlong
 * @date 2025年07月31日
 * @version: 1.0
 */
public class ResponseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 设置响应字符编码为UTF-8
        response.setCharacterEncoding("UTF-8");
        return true;
    }
}
