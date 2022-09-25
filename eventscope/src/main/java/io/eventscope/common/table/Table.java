package io.eventscope.common.table;

import io.eventscope.db.clickhouse.engine.Engine;
import org.apache.commons.lang3.StringUtils;


public interface Table {

    String getName();

    String getSchema();

    String getClusterName();

    Engine getEngine();

    default String getPartitionBy() {
        return null;
    }

    default String getOrderBy() {
        return null;
    }

    default String getSampleBy() {
        return null;
    }

    default String getSettings() {
        return null;
    }

    default String getCreateQuery() {

        return String.format("""
                CREATE TABLE IF NOT EXISTS default.%s ON CLUSTER %s
                (
                %s
                )
                ENGINE = %s                
                """, getName(), getClusterName(), getSchema(), this.getEngine().getEngine())

                + (StringUtils.isNotBlank(getPartitionBy()) ?
                String.format("""
                        PARTITION BY %s
                        """, getPartitionBy()) : "")

                + (StringUtils.isNotBlank(getOrderBy()) ?
                String.format("""
                        ORDER BY %s
                        """, getOrderBy()) : "")


                + (StringUtils.isNotBlank(getSampleBy()) ?
                String.format("""
                        SAMPLE BY %s
                        """, getSampleBy()) : "")

                + (StringUtils.isNotBlank(getSettings()) ?
                String.format("""
                        SETTINGS
                         %s
                        """, getSettings()) : "")
                ;
    }

    default String getRollbackQuery() {
        return String.format("""
                DROP TABLE IF EXISTS %s on cluster %s           
                """, getName(), getClusterName());

    }
}
