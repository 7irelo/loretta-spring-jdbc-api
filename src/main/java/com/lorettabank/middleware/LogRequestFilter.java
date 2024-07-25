package com.lorettabank.middleware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class LogRequestFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LogRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();
        logger.info("Request: {} {} from {}", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getRemoteAddr());

        chain.doFilter(request, response);

        long duration = System.currentTimeMillis() - startTime;
        logger.info("Response: {} {} from {} in {}ms", httpRequest.getMethod(), httpRequest.getRequestURI(), httpRequest.getRemoteAddr(), duration);
    }

    @Override
    public void destroy() {
        // Cleanup
    }
}
