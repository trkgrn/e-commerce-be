package com.trkgrn.common.kafka.converters;

import com.trkgrn.common.avro.EventMetadata;
import com.trkgrn.common.kafka.AvroEventConverter;

import java.util.Map;

public abstract class BaseEventConverter implements AvroEventConverter {

    protected EventMetadata convertMetadata(Map<String, Object> metadataMap) {
        if (metadataMap == null) {
            throw new IllegalArgumentException("Event metadata cannot be null");
        }

        return EventMetadata.newBuilder()
                .setTraceId(getStringValue(metadataMap, "traceId"))
                .setCorrelationId(getStringValue(metadataMap, "correlationId"))
                .setTimestamp(getLongValue(metadataMap, "timestamp"))
                .setEventId(getStringValue(metadataMap, "eventId"))
                .setEventVersion(getStringValue(metadataMap, "eventVersion", "1.0"))
                .setSource(getStringValue(metadataMap, "source"))
                .build();
    }

    protected String getStringValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Required field '" + key + "' is missing");
        }
        return value.toString();
    }

    protected String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        Object value = map.get(key);
        return value != null ? value.toString() : defaultValue;
    }

    protected Long getLongValue(Map<String, Object> map, String key) {
        Object value = map.get(key);
        if (value == null) {
            throw new IllegalArgumentException("Required field '" + key + "' is missing");
        }

        if (value instanceof Number) {
            return ((Number) value).longValue();
        }

        try {
            return Long.parseLong(value.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Field '" + key + "' must be a valid number");
        }
    }
}