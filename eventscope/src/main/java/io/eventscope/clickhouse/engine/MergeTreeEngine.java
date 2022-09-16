package io.eventscope.clickhouse.engine;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;


@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class MergeTreeEngine {

    final String table;
    final ReplicationSchema replicationSchema;
    final String engine;
    final String replicatedEngine;
    String zookeeperPathKey;

    public MergeTreeEngine(String table, String engine, String replicatedEngine, ReplicationSchema replicationSchema) {
        this.table = table;
        this.engine = engine;
        this.replicatedEngine = replicatedEngine;
        this.replicationSchema = replicationSchema;
    }

    public void setZookeeperPathKey(String zookeeperPathKey) {
        this.zookeeperPathKey = zookeeperPathKey;
    }

}
