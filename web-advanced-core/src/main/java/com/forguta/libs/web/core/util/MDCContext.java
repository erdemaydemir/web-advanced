package com.forguta.libs.web.core.util;

import com.forguta.libs.web.core.constant.LogConstant;
import org.slf4j.MDC;

public class MDCContext {

    private static final String CORRELATION_ID = LogConstant.CORRELATION_ID;

    public static void put(String correlationId) {
        MDC.put(CORRELATION_ID, correlationId);
    }

    public static String getCorrelationId() {
        return MDC.get(CORRELATION_ID);
    }

    public static void clear() {
        MDC.clear();
    }
}
