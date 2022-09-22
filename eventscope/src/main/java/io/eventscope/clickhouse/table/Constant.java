package io.eventscope.clickhouse.table;

public class Constant {

    public static final String EVENT_SCHEMA = """
                                id String,
                                timestamp DateTime64(3, 'UTC'),
                                properties JSON
            """;

    public static final String KAFKA_EVENT_SCHEMA = """
                                raw String
            """;

    public static final String KAFKA_EVENT_SELECT = """
                               JSONExtract(raw, 'id', 'String') as id,
                               parseDateTimeBestEffort(JSONExtractString(raw, 'timeStamp')) as timestamp,
                               JSONExtractRaw(raw, 'properties') as properties
            """;
}
