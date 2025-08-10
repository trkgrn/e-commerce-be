package com.trkgrn.common.utils;

import brave.Tracing;
import brave.propagation.TraceContext;
import feign.RequestTemplate;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.function.BiConsumer;

import static com.trkgrn.common.constants.Headers.*;
import static com.trkgrn.common.constants.TracingConstants.CORRELATION_ID;
import static com.trkgrn.common.constants.TracingConstants.TRACE_ID;

public final class TracingUtils {

    private static final Logger logger = LoggerFactory.getLogger(TracingUtils.class);

    private TracingUtils() {
    }


    private static void addHeaders(Tracing tracing, BiConsumer<String, String> headerAdder) {
        if (Objects.isNull(tracing))
            return;

        TraceContext ctx = tracing.currentTraceContext().get();
        if (Objects.isNull(ctx))
            return;

        headerAdder.accept(X_B_3_TRACE_ID, ctx.traceIdString());
        headerAdder.accept(X_B_3_SPAN_ID, ctx.spanIdString());

        if (Objects.nonNull(ctx.parentId())) {
            headerAdder.accept(X_B_3_PARENT_SPAN_ID, ctx.parentIdString());
        }
        headerAdder.accept(X_B_3_SAMPLED, ctx.sampled() ? "1" : "0");
    }

    public static void addBraveHeaders(ServerHttpRequest.Builder requestBuilder, Tracing tracing) {
        addHeaders(tracing, requestBuilder::header);
    }

    public static void addBraveHeaders(RequestTemplate template, Tracing tracing) {
        addHeaders(tracing, template::header);
    }

    public static String extractTraceId(Tracer tracer, Tracing tracing) {
        if (Objects.nonNull(tracer) && Objects.nonNull(tracer.currentSpan())) {
            return Objects.requireNonNull(tracer.currentSpan()).context().traceId();
        }

        if (Objects.nonNull(tracing)) {
            TraceContext context = tracing.currentTraceContext().get();
            if (Objects.nonNull(context)) {
                return context.traceIdString();
            }
        }

        return null;
    }

    public static String getCurrentRequestCorrelationId() {
        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (Objects.nonNull(requestAttributes)) {
                String correlationId = requestAttributes.getRequest().getHeader(X_CORRELATION_ID);
                if (Objects.nonNull(correlationId)) {
                    correlationId = requestAttributes.getRequest().getHeader(X_TRACE_ID);
                }
                return correlationId;
            }
        } catch (Exception e) {
            logger.debug("Error getting correlation ID from request", e);
        }
        return null;
    }

    public static String getCorrelationIdWithMDC() {
        String correlationId = MDC.get(CORRELATION_ID);
        if (Objects.nonNull(correlationId)) {
            return correlationId;
        }

        return TracingUtils.getCurrentRequestCorrelationId();
    }

    public static String getTraceIdWithMDC(Tracer tracer, Tracing tracing) {
        String traceId = MDC.get(TRACE_ID);
        if (Objects.nonNull(traceId)) {
            return traceId;
        }

        return TracingUtils.extractTraceId(tracer, tracing);
    }

}
