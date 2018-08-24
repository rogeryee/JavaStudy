package com.yee.study.spring.cloud.zuul.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServletRequest;

/**
 * 前置请求过滤器
 * 
 * @author Roger.Yi
 */
public class PreRequestFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(PreRequestFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        logger.info("Receive request, method=[{}], url=[{}]", request.getMethod(), request.getRequestURL().toString());
        return null;
    }
}
