package com.trkgrn.common.kafka;

import org.apache.avro.specific.SpecificRecord;

public interface AvroEventConverter {

    /**
     * Convert generic payload Map to specific Avro record
     */
    SpecificRecord convertToAvro(KafkaMessage message);

    /**
     * Get the schema name this converter handles
     */
    String getSchemaName();

    /**
     * Check if this converter supports the given schema
     */
    default boolean supports(String schemaName) {
        return getSchemaName().equals(schemaName);
    }
}
