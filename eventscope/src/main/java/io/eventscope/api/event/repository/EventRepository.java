package io.eventscope.api.event.repository;

import io.eventscope.api.event.mapper.EventMapper;
import io.eventscope.api.event.model.Event;
import io.eventscope.api.event.model.EventEntity;
import io.eventscope.db.clickhouse.ClickhouseRepository;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class EventRepository {

    public static final Class<EventEntity> clazz = EventEntity.class;
    @ConfigProperty(name = "eventscope.clickhouse.cluster")
    String clickhouseCluster;
    @Inject
    EventMapper mapper;
    @Inject
    ClickhouseRepository repository;

    public Uni<Event> getById(String tableName, String id) {
        String query = String.format("SELECT * FROM %s on %s WHERE id=%s", tableName, id);
        return repository.runQuery(query, clazz).collect().asList()
                .map(list -> mapper.toFirst(list.get(0)));
    }
}
