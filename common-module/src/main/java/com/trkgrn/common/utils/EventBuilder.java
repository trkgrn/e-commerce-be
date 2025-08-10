package com.trkgrn.common.utils;

import brave.Tracing;
import com.trkgrn.common.avro.EventMetadata;
import io.micrometer.tracing.Tracer;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EventBuilder {

    @Resource
    private Tracer tracer;

    @Resource
    private Tracing tracing;

    public EventMetadata buildEventMetadata(String source) {
        return EventMetadata.newBuilder()
                .setTraceId(TracingUtils.getTraceIdWithMDC(tracer, tracing))
                .setCorrelationId(TracingUtils.getCorrelationIdWithMDC())
                .setEventVersion("1.0")
                .setSource(source)
                .setTimestamp(System.currentTimeMillis())
                .setEventId(java.util.UUID.randomUUID().toString())
                .build();
    }

}