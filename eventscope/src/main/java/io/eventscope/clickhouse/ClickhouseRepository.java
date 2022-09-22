package io.eventscope.clickhouse;

import com.clickhouse.client.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.eventscope.clickhouse.table.Table;
import io.eventscope.clickhouse.view.View;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.unchecked.Unchecked;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.ObjectUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ApplicationScoped
public class ClickhouseRepository {

    final ClickHouseClient client;

    final ClickHouseRequest<?> request;

    final ObjectMapper objectMapper;


    @Inject
    public ClickhouseRepository(@ConfigProperty(name = "clickhouse.hosts") String hosts) {
        ClickHouseNodes servers = ClickHouseNodes.of(hosts);
        this.client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
        this.request = client.connect(servers).format(ClickHouseFormat.JSONEachRow);

        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
                .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }


    public Uni<Void> createTable(Table table) {
        String query = table.getCreateQuery();

        return this.runQuery(query)
                .collect().asList()
                .replaceWithVoid();
    }

    public Uni<Void> createView(View view) {
        String query = view.getCreateQuery();

        return this.runQuery(query)
                .collect().asList()
                .replaceWithVoid();
    }

    public Uni<Void> deleteTable(Table table) {
        String query = table.getRollbackQuery();

        return this.runQuery(query)
                .collect().asList()
                .replaceWithVoid();
    }

    public Uni<Void> deleteView(View view) {
        String query = view.getRollbackQuery();

        return this.runQuery(query)
                .collect().asList()
                .replaceWithVoid();
    }


    public <T> Multi<T> runQuery(String query) {
        return this.runQuery(query, null);
    }

    public <T> Multi<T> runQuery(String query, Class<T> clazz) {
        return this.runQuery(query, null, clazz);
    }

    public <T> Multi<T> runQuery(String query, Integer position, Class<T> clazz) {
        return Uni.createFrom().completionStage(() -> request.query(query).execute())
                .map(response -> response.stream().filter(ObjectUtils::isNotEmpty).collect(Collectors.toList()))
                .onItem().transformToMulti(records -> Multi.createFrom().iterable(records))
                .map(Unchecked.function(record -> getValue(record, position, clazz)));
    }

    private <T> T getValue(ClickHouseRecord record, Integer position, Class<T> clazz) throws JsonProcessingException {
        if (position == null || position < 0) position = 0;

        if (clazz == null) {
            return this.objectMapper.readValue(record.getValue(position).asString(), new TypeReference<T>() {
            });
        } else {
            return this.objectMapper.readValue(record.getValue(position).asString(), clazz);
        }
    }

}
