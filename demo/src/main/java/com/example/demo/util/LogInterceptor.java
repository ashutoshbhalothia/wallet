package com.example.demo.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LogInterceptor implements HandlerInterceptor {

    private final Logger LOGGER = LoggerFactory.getLogger(LogInterceptor.class);
    private static final String BASIC = "Basic";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        LOGGER.info("preHandle");

        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith(BASIC)){
            String base64Credentials = authHeader.substring(BASIC.length());
            base64Credentials = base64Credentials.trim();
            byte[] decodedCredentials = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(decodedCredentials, StandardCharsets.UTF_8);

            String[] parts = credentials.split(":");

            if (USERNAME.equals(parts[0]) && PASSWORD.equals(parts[1]))
                return Boolean.TRUE;
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Unauthorized User");
        return Boolean.FALSE;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        LOGGER.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        LOGGER.info("afterCompletion");
    }

}
