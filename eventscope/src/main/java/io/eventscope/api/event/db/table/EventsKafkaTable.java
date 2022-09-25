package io.eventscope.api.event.db.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.api.event.db.Schema;
import io.eventscope.db.clickhouse.engine.integration.kafka.KafkaEngine;
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

    final KafkaEngine engine;
    final String clusterName;

    public EventsKafkaTable(String clusterName, KafkaEngine engine) {
        super(EventsKafkaTable.builder()
                .clusterName(clusterName)
                .engine(engine)
                .name(name)
                .schema(schema));
        this.clusterName = clusterName;
        this.engine = engine;
    }

}
