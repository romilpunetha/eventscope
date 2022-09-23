package io.eventscope.clickhouse.table.schema;

public class Schema {

    public static final String EVENT_SCHEMA = """
                                id String CODEC(ZSTD(1)),
                                idempotentId String CODEC(ZSTD(1)),
                                name LowCardinality(String) CODEC(ZSTD(1)), 
                                userId String CODEC(ZSTD(1)),
                                sessionId String CODEC(ZSTD(1)),  
                                properties JSON CODEC(ZSTD(3)),
                                timestamp DateTime64(3) CODEC(Delta, ZSTD(1)),
                                createdAt DateTime64(3) CODEC(Delta, ZSTD(1)),
                                tenantId LowCardinality(String) CODEC(ZSTD(1))
            """;

    public static final String KAFKA_EVENT_SCHEMA = """
                                raw String
            """;

    public static final String KAFKA_EVENT_SELECT = """
                               JSONExtract(raw, 'id', 'String') as id,
                               JSONExtract(raw, 'idempotentId', 'String') as idempotentId,
                               JSONExtract(raw, 'name', 'String') as name,
                               JSONExtract(raw, 'userId', 'String') as userId,
                               JSONExtract(raw, 'sessionId', 'String') as sessionId,
                               JSONExtractRaw(raw, 'properties') as properties,
                               parseDateTimeBestEffort(JSONExtractString(raw, 'timestamp')) as timestamp,
                               parseDateTimeBestEffort(JSONExtractString(raw, 'createdAt')) as createdAt,
                               JSONExtract(raw, 'tenantId', 'String') as tenantId
            """;
}
