package io.eventscope.clickhouse;

import com.clickhouse.client.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventscope.clickhouse.engine.MergeTreeEngine;
import io.smallrye.mutiny.Uni;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ApplicationScoped
public class ClickhouseRepository {

    final ClickHouseClient client;

    final ClickHouseRequest<?> request;

    final ObjectMapper objectMapper;


    @Inject
    public ClickhouseRepository(@ConfigProperty(name = "clickhouse.hosts", defaultValue = "jdbc:ch:http://localhost:8123/my_db") String hosts) {
        ClickHouseNodes servers = ClickHouseNodes.of(hosts);
        this.client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
        this.request = client.connect(servers).format(ClickHouseFormat.JSONEachRow);

        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }


    public Uni<Void> createTable(String tableName,
                                 String clusterName,
                                 String schema,
                                 MergeTreeEngine engine,
                                 String orderBy,
                                 String storagePolicy
    ) {
        String query = String.format("""
                CREATE TABLE IF NOT EXISTS %s ON CLUSTER %s
                (
                %s
                ) ENGINE = %s
                ORDER_BY %s
                %s
                """, tableName, clusterName, schema, engine.toString(), orderBy, storagePolicy);

        return Uni.createFrom().completionStage(request.query(query).execute())
                .replaceWithVoid();
    }

}
