package io.eventscope.clickhouse.engine;

import lombok.Getter;

public enum InputFormat {

    JSONEachRow("JSONEachRow"),
    JSONAsString("JSONAsString");

    @Getter
    private final String value;

    InputFormat(String value) {
        this.value = value;
    }
}
