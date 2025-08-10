package com.trkgrn.common.kafka;

import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class EventConverterRegistry {

    private final Map<String, AvroEventConverter> converters;

    public EventConverterRegistry(List<AvroEventConverter> converterList) {
        this.converters = converterList.stream()
                .collect(Collectors.toMap(
                        AvroEventConverter::getSchemaName,
                        Function.identity()
                ));
    }

    public SpecificRecord convertToAvro(KafkaMessage message) {
        AvroEventConverter converter = converters.get(message.getSchemaName());
        if (converter == null) {
            throw new IllegalArgumentException("No converter found for schema: " + message.getSchemaName());
        }
        return converter.convertToAvro(message);
    }

    public boolean supportsSchema(String schemaName) {
        return converters.containsKey(schemaName);
    }
}