package io.eventscope.clickhouse.engine;

public class ReplacingMergeTree extends MergeTreeEngine {


    public ReplacingMergeTree(String table, ReplicationSchema replicationSchema) {
        super(table,
                "ReplacingMergeTree({0})",
                "ReplicatedReplacingMergeTree('{0}', '{1}', {2})",
                replicationSchema);
    }
}
