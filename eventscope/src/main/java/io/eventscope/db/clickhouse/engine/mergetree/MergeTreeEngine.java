package io.eventscope.db.clickhouse.engine.mergetree;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.eventscope.db.clickhouse.engine.Engine;
import io.eventscope.db.clickhouse.engine.EngineFamily;
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
public abstract class MergeTreeEngine implements Engine {

    static final EngineFamily engineFamily = EngineFamily.MERGE_TREE;
    final String table;
    final ReplicationSchema replicationSchema;
    final String engine;

    final String replicatedEngine;
    String zookeeperPathKey;

    public void setZookeeperPathKey(String zookeeperPathKey) {
        this.zookeeperPathKey = zookeeperPathKey;
    }

    public String getEngine() {
        return this.engine;
    }

}
