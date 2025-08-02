package com.trkgrn.common.interceptor;

import brave.Tracing;
import com.trkgrn.common.utils.TracingUtils;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;
import java.util.UUID;

import static com.trkgrn.common.constants.Headers.X_CORRELATION_ID;
import static com.trkgrn.common.constants.Headers.X_TRACE_ID;
import static com.trkgrn.common.constants.TracingConstants.CORRELATION_ID;
import static com.trkgrn.common.constants.TracingConstants.TRACE_ID;

@Component
public class TraceInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);

    @Resource
    private Tracer tracer;
    @Resource
    private Tracing tracing;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String correlationId = extractCorrelationId(request);
        String traceId = TracingUtils.extractTraceId(tracer, tracing);

        MDC.put(CORRELATION_ID, correlationId);

        if (Objects.nonNull(traceId)) {
            MDC.put(TRACE_ID, traceId);
        }

        response.setHeader(X_CORRELATION_ID, correlationId);
        if (Objects.nonNull(traceId)) {
            response.setHeader(X_TRACE_ID, traceId);
        }

        logger.debug("Request intercepted - correlationId: {}, traceId: {}, path: {}",
                correlationId, traceId, request.getRequestURI());

        return true;
    }

    private String extractCorrelationId(HttpServletRequest request) {
        String correlationId = request.getHeader(X_CORRELATION_ID);

        if (Objects.isNull(correlationId)) {
            correlationId = request.getHeader(X_TRACE_ID);
        }

        if (Objects.isNull(correlationId)) {
            correlationId = TracingUtils.extractTraceId(tracer, tracing);
        }

        if (Objects.isNull(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        return correlationId;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        MDC.clear();
    }
}