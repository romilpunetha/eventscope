package io.eventscope.db.clickhouse.engine;

public enum EngineFamily {

    MERGE_TREE("MergeTree"),
    LOG("Log"),
    INTEGRATION_ENGINES("IntegrationEngines"),
    SPECIAL_ENGINES("SpecialEngines");

    final String value;

    EngineFamily(String value) {
        this.value = value;
    }
}
