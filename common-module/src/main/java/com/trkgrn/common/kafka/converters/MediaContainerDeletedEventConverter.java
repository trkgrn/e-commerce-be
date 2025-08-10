package com.trkgrn.common.kafka.converters;

import com.trkgrn.common.avro.MediaContainerDeletedEvent;
import com.trkgrn.common.avro.VariantProductDeletedEvent;
import com.trkgrn.common.kafka.KafkaMessage;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MediaContainerDeletedEventConverter extends BaseEventConverter {

    @Override
    public String getSchemaName() {
        return "MediaContainerDeletedEvent";
    }

    @Override
    public SpecificRecord convertToAvro(KafkaMessage message) {
        Map<String, Object> payload = message.getPayload();

        Map<String, Object> metadataMap = (Map<String, Object>) payload.get("metadata");
        if (metadataMap == null) {
            throw new IllegalArgumentException("Event metadata is required");
        }

        return MediaContainerDeletedEvent.newBuilder()
                .setMetadata(convertMetadata(metadataMap))
                .setMediaContainerId(getLongValue(payload, "mediaContainerId"))
                .build();
    }
}