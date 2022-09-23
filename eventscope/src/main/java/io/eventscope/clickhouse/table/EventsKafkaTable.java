package io.eventscope.clickhouse.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.clickhouse.engine.integration.kafka.KafkaEngine;
import io.eventscope.clickhouse.table.schema.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EventsKafkaTable extends KafkaTable {

    static final String name = "events_kafka";

    static final String schema = Schema.KAFKA_EVENT_SCHEMA;

    static final String settings = """
            kafka_skip_broken_messages = 100
            """;

    final KafkaEngine engine;
    final String clusterName;

    public EventsKafkaTable(String clusterName, KafkaEngine engine) {
        super(EventsKafkaTable.builder()
                .clusterName(clusterName)
                .engine(engine)
                .name(name)
                .schema(schema)
                .settings(settings));
        this.clusterName = clusterName;
        this.engine = engine;
    }

}
