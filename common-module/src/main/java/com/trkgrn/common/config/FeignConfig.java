package com.trkgrn.common.config;

import brave.Tracing;
import com.trkgrn.common.utils.TracingUtils;
import feign.RequestInterceptor;
import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.Objects;

import static com.trkgrn.common.constants.Headers.X_CORRELATION_ID;
import static com.trkgrn.common.constants.Headers.X_TRACE_ID;
import static com.trkgrn.common.constants.TracingConstants.CORRELATION_ID;
import static com.trkgrn.common.constants.TracingConstants.TRACE_ID;

@Configuration
@EnableFeignClients(basePackages = "com.trkgrn")
public class FeignConfig {

    private static final Logger logger = LoggerFactory.getLogger(FeignConfig.class);

    private final Tracer tracer;
    private final Tracing tracing;

    public FeignConfig(Tracer tracer, Tracing tracing) {
        this.tracer = tracer;
        this.tracing = tracing;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            String correlationId = TracingUtils.getCorrelationIdWithMDC();
            String traceId = TracingUtils.extractTraceId(tracer, tracing);

            if (Objects.nonNull(correlationId)) {
                template.header(X_CORRELATION_ID, correlationId);
            }

            if (Objects.nonNull(traceId)) {
                template.header(X_TRACE_ID, traceId);
                if (Objects.isNull(correlationId)) {
                    template.header(X_CORRELATION_ID, traceId);
                }
            }

            TracingUtils.addBraveHeaders(template, tracing);

            template.header(HttpHeaders.CONTENT_TYPE, "application/json");

            logger.debug("Feign request intercepted - correlationId: {}, traceId: {}, target: {}",
                    correlationId, traceId, template.feignTarget());
        };
    }

}
