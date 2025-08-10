package com.trkgrn.common.kafka.converters;

import com.trkgrn.common.avro.VariantProductDeletedEvent;
import com.trkgrn.common.kafka.KafkaMessage;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VariantProductDeletedEventConverter extends BaseEventConverter {

    @Override
    public String getSchemaName() {
        return "VariantProductDeletedEvent";
    }

    @Override
    public SpecificRecord convertToAvro(KafkaMessage message) {
        Map<String, Object> payload = message.getPayload();

        Map<String, Object> metadataMap = (Map<String, Object>) payload.get("metadata");
        if (metadataMap == null) {
            throw new IllegalArgumentException("Event metadata is required");
        }

        return VariantProductDeletedEvent.newBuilder()
                .setMetadata(convertMetadata(metadataMap))
                .setProductId(getLongValue(payload, "productId"))
                .build();
    }
}