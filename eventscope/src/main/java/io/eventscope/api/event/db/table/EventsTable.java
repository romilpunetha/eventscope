package io.eventscope.api.event.db.table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.api.event.db.Schema;
import io.eventscope.common.table.Table;
import io.eventscope.db.clickhouse.engine.ReplicationSchema;
import io.eventscope.db.clickhouse.engine.mergetree.MergeTreeEngine;
import io.eventscope.db.clickhouse.engine.mergetree.ReplacingMergeTree;
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
public class EventsTable implements Table {

    final String name = "events";

    final String schema = Schema.EVENT_SCHEMA;

    final MergeTreeEngine engine = new ReplacingMergeTree(name,
            ReplicationSchema.SHARDED,
            "timestamp"
    );

    final String orderBy = """
            (timestamp)
            """;
    final String clusterName;

    public EventsTable(String clusterName) {
        this.clusterName = clusterName;
    }

}
