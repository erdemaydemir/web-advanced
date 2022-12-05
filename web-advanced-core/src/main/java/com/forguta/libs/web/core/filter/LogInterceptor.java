package com.forguta.libs.web.core.filter;

import com.forguta.libs.web.core.constant.HttpPhaseEnum;
import com.forguta.libs.web.core.constant.LogConstant;
import com.forguta.libs.web.core.util.MDCContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
public class LogInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String correlationId = request.getHeader(LogConstant.CORRELATION_ID);
        String requestId = UUID.randomUUID().toString();
        if (StringUtils.isEmpty(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }
        MDCContext.put(correlationId);
        response.addHeader(LogConstant.CORRELATION_ID, correlationId);
        response.addHeader(LogConstant.REQUEST_ID, requestId);
        logger.info("[{}] REQUEST [{}] -> id = {}, correlation-id = {}, phase = {}", request.getMethod(), request.getRequestURI(), requestId, correlationId, HttpPhaseEnum.PRE_HANDLE);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String correlationId = response.getHeader(LogConstant.CORRELATION_ID);
        String requestId = response.getHeader(LogConstant.REQUEST_ID);
        logger.info("[{}] REQUEST [{}] -> id = {}, correlation-id = {}, phase = {}", request.getMethod(), request.getRequestURI(), requestId, correlationId, HttpPhaseEnum.POST_HANDLE);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String correlationId = response.getHeader(LogConstant.CORRELATION_ID);
        String requestId = response.getHeader(LogConstant.REQUEST_ID);
        logger.info("[{}] REQUEST [{}] -> id = {}, correlation-id = {}, phase = {}, exception = {}", request.getMethod(), request.getRequestURI(), requestId, correlationId, HttpPhaseEnum.AFTER_COMPLETION, ex);
    }
}
