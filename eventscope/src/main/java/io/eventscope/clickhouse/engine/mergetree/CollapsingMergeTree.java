package io.eventscope.clickhouse.engine.mergetree;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.clickhouse.engine.ReplicationSchema;
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
public class CollapsingMergeTree extends MergeTreeEngine {

    public CollapsingMergeTree(String table, ReplicationSchema replicationSchema, String version, String zkPath, String replicaKey) {
        super(CollapsingMergeTree.builder()
                .table(table)
                .engine(String.format("CollapsingMergeTree(%s)", version))
                .replicatedEngine(String.format("ReplicatedCollapsingMergeTree('%s', '%s', %s)", zkPath, replicaKey, version))
                .replicationSchema(replicationSchema)
        );
    }

    public CollapsingMergeTree(String table, ReplicationSchema replicationSchema, String version) {
        super(CollapsingMergeTree.builder()
                .table(table)
                .engine(String.format("CollapsingMergeTree(%s)", version))
                .replicationSchema(replicationSchema)
        );
    }

}
