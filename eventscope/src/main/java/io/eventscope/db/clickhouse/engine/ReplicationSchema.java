package io.eventscope.db.clickhouse.engine;

public enum ReplicationSchema {
    NOT_SHARDED("NOT_SHARDED"),
    SHARDED("SHARDED"),
    REPLICATED("REPLICATED");

    final String value;


    ReplicationSchema(String value) {
        this.value = value;
    }
}
