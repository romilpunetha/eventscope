package io.eventscope.clickhouse.view;

public interface View {

    String getCreateQuery();

    String getRollbackQuery();
}
