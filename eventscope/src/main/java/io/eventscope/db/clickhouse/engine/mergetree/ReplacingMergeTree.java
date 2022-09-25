package io.eventscope.db.clickhouse.engine.mergetree;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.db.clickhouse.engine.ReplicationSchema;
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
public class ReplacingMergeTree extends MergeTreeEngine {

    public ReplacingMergeTree(String table, ReplicationSchema replicationSchema, String version, String zkPath, String replicaKey) {
        super(ReplacingMergeTree.builder()
                .table(table)
                .engine(String.format("ReplacingMergeTree(%s)", version))
                .replicatedEngine(String.format("ReplicatedReplacingMergeTree(%s, %s, %s)", zkPath, replicaKey, version))
                .replicationSchema(replicationSchema)
        );
    }

    public ReplacingMergeTree(String table, ReplicationSchema replicationSchema, String version) {
        super(ReplacingMergeTree.builder()
                .table(table)
                .engine(String.format("ReplacingMergeTree(%s)", version))
                .replicationSchema(replicationSchema)
        );
    }

}
