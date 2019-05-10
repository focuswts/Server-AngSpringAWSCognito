package com.spring.angular.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Order(1)
public class RequestFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
//        LOG.info(
//                "Logging Request  {} : {}", req.getMethod(),
//                req.getRequestURI());
//        chain.doFilter(request, response);
//        LOG.info(
//                "Logging Response :{}",
//                res.getContentType());

        SecurityContextHolder.getContext().setAuthentication(null);
    }

}
