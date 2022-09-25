package io.eventscope.api.event.db.materializedview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.api.event.db.Schema;
import io.eventscope.common.table.materializedview.MaterializedView;
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
public class KafkaToEventsMaterializedView extends MaterializedView {

    static final String name = "events_mv";

    static final String fromTable = "events_kafka";

    static final String toTable = "events";

    static final String select = Schema.KAFKA_EVENT_SELECT;

    public KafkaToEventsMaterializedView(String clusterName) {
        super(KafkaToEventsMaterializedView.builder()
                .name(name)
                .fromTable(fromTable)
                .toTable(toTable)
                .select(select)
                .clusterName(clusterName));
    }
}
