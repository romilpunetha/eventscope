package io.eventscope.clickhouse.engine;

public class CollapsingMergeTree extends MergeTreeEngine {


    public CollapsingMergeTree(String table, ReplicationSchema replicationSchema) {
        super(table,
                "CollapsingMergeTree({0})",
                "ReplicatedCollapsingMergeTree('{0}', '{1}', {2})",
                replicationSchema);
    }
}
