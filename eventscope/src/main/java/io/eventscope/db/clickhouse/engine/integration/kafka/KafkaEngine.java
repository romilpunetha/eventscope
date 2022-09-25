package io.eventscope.db.clickhouse.engine.integration.kafka;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.db.clickhouse.engine.Engine;
import io.eventscope.db.clickhouse.engine.EngineFamily;
import io.eventscope.db.clickhouse.engine.InputFormat;
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
public class KafkaEngine implements Engine {

    static final EngineFamily engineFamily = EngineFamily.INTEGRATION_ENGINES;

    final String brokers;

    final String topic;

    final String group;

    final InputFormat format;

    public KafkaEngine(String brokers, String topic, String group, InputFormat format) {
        this.brokers = brokers;
        this.topic = topic;
        this.group = group;
        this.format = format;
    }

    public String getEngine() {
        return String.format("Kafka('%s', '%s', '%s', '%s')", getBrokers(), getTopic(), getGroup(), getFormat().getValue());
    }
}
