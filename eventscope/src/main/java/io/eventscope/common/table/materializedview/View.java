package io.eventscope.common.table.materializedview;

public interface View {

    String getCreateQuery();

    String getRollbackQuery();
}
