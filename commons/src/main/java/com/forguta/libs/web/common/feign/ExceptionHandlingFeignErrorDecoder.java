package com.forguta.libs.web.common.feign;

import com.forguta.commons.context.MyApplicationContext;
import feign.Feign;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Lazy
@Component
@Slf4j
public class ExceptionHandlingFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder.Default defaultDecoder = new Default();
    private final Map<String, FeignHttpExceptionHandler> exceptionHandlerMap = new HashMap<>();

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<String, APIClient> proxies = MyApplicationContext.getApplicationContext().getBeansOfType(APIClient.class);
        List<Method> clientMethods = proxies.values().stream()
                .map(Object::getClass)
                .map(aClass -> aClass.getInterfaces()[0])
                .map(ReflectionUtils::getDeclaredMethods)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        for (Method m : clientMethods) {
            String configKey = Feign.configKey(m.getDeclaringClass(), m);
            HandleFeignError handlerAnnotation = getHandleFeignErrorAnnotation(m);
            if (handlerAnnotation != null) {
                FeignHttpExceptionHandler handler = MyApplicationContext.getApplicationContext().getBean(handlerAnnotation.value());
                exceptionHandlerMap.put(configKey, handler);
            }
        }
    }

    private HandleFeignError getHandleFeignErrorAnnotation(Method m) {
        HandleFeignError result = m.getAnnotation(HandleFeignError.class);
        if (result == null) {
            result = m.getDeclaringClass().getAnnotation(HandleFeignError.class);
        }
        return result;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        FeignHttpExceptionHandler handler = exceptionHandlerMap.get(methodKey);
        if (handler != null) {
            try {
                return handler.handle(response);
            } catch (Exception exception) {
                log.error("FeignHttpExceptionHandler cannot handle error response. {}", exception.getMessage(), exception);
                return defaultDecoder.decode(methodKey, response);
            }
        }
        return defaultDecoder.decode(methodKey, response);
    }
}
