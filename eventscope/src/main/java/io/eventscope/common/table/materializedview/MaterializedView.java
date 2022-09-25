package io.eventscope.common.table.materializedview;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class MaterializedView implements View {

    final String name;

    final String fromTable;

    final String toTable;

    final String select;

    final String clusterName;

    public String getCreateQuery() {
        return String.format("""
                CREATE MATERIALIZED VIEW IF NOT EXISTS default.%s TO %s
                AS
                SELECT
                (
                %s
                )
                FROM %s;
                """, getName(), getToTable(), getSelect(), getFromTable());
    }

    public String getRollbackQuery() {
        return String.format("""
                DROP TABLE IF EXISTS %s on cluster %s         
                """, getName(), getClusterName());

    }
}
