package com.trkgrn.common.kafka;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.avro.Schema;
import org.apache.avro.specific.SpecificRecord;

import java.util.HashMap;
import java.util.Map;

public class KafkaMessage {
    private final String schemaName;
    private final String schemaVersion;
    private final Map<String, Object> payload;

    @JsonCreator
    public KafkaMessage(
            @JsonProperty("schemaName") String schemaName,
            @JsonProperty("schemaVersion") String schemaVersion,
            @JsonProperty("payload") Map<String, Object> payload) {
        this.schemaName = schemaName;
        this.schemaVersion = schemaVersion;
        this.payload = payload;
    }

    public static KafkaMessage from(SpecificRecord avroRecord) {
        return new KafkaMessage(
                avroRecord.getSchema().getName(),
                getSchemaVersion(avroRecord.getSchema()),
                convertToPayload(avroRecord)
        );
    }

    private static String getSchemaVersion(Schema schema) {
        return schema.getProp("version") != null ? schema.getProp("version") : "1.0";
    }

    private static Map<String, Object> convertToPayload(SpecificRecord record) {
        Map<String, Object> payload = new HashMap<>();
        for (Schema.Field field : record.getSchema().getFields()) {
            Object value = record.get(field.pos());
            payload.put(field.name(), convertAvroValue(value));
        }
        return payload;
    }

    private static Object convertAvroValue(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof SpecificRecord) {
            return convertToPayload((SpecificRecord) value);
        }

        if (value instanceof org.apache.avro.util.Utf8) {
            return value.toString();
        }

        return value;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getSchemaVersion() {
        return schemaVersion;
    }

    public Map<String, Object> getPayload() {
        return payload;
    }
}
