import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventscope.api.event.db.materializedview.KafkaToEventsMaterializedView;
import io.eventscope.api.event.db.table.EventsKafkaTable;
import io.eventscope.api.event.db.table.EventsTable;
import io.eventscope.api.event.model.Event;
import io.eventscope.api.event.service.EventScopeService;
import io.eventscope.common.table.Table;
import io.eventscope.common.table.materializedview.View;
import io.eventscope.db.clickhouse.ClickhouseRepository;
import io.eventscope.db.clickhouse.engine.InputFormat;
import io.eventscope.db.clickhouse.engine.integration.kafka.KafkaEngine;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestKafkaInjection {

    Table eventsTable;
    Table eventsKafkaTable;

    View kafkaToEventsView;

    String eventsTopic = "eventscope.events";
    String testGroup = "test.group";

    @Inject
    ClickhouseRepository repository;
    @Inject
    EventScopeService service;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public void init() {

        String clusterName = "cluster_1";

        String kafkaBrokers = "clickhouse-kafka:9092";

        this.eventsTable = new EventsTable(clusterName);

        KafkaEngine kafkaEngine = new KafkaEngine(kafkaBrokers, eventsTopic, testGroup, InputFormat.JSONAsString);
        this.eventsKafkaTable = new EventsKafkaTable(clusterName, kafkaEngine);

        kafkaToEventsView = new KafkaToEventsMaterializedView(clusterName);

        repository.runQuery("SET allow_experimental_object_type=1;").collect().asList().await().indefinitely();
        repository.createTable(eventsTable).await().indefinitely();
        repository.createTable(eventsKafkaTable).await().indefinitely();
        repository.createView(kafkaToEventsView).await().indefinitely();

    }

    @AfterAll
    public void destroy() {
//        repository.deleteTable(eventsTable).await().indefinitely();
//        repository.deleteTable(eventsKafkaTable).await().indefinitely();
//        repository.deleteView(kafkaToEventsView).await().indefinitely();
    }

    @Test
    public void addEventToClickhouse() throws JsonProcessingException, InterruptedException {

        String j1String = "{\"isPositive\" : \"true\"}";
        JsonNode j1 = mapper.readTree(j1String);

        String j2String = "{\r\n    \"isPositive\" : false,\r\n    \"isCompleted\": [true, false],\r\n    \"count\": 1,\r\n  \"user\" : {\r\n    \"isLoggedIn\": true,\r\n    \"location\": \"nearby\"\r\n    },\r\n  \"element\" : {\r\n    \"buttonClicked\": \"affirmative\"\r\n  }\r\n}";
        JsonNode j2 = mapper.readTree(j2String);

        Event event = Event.builder()
                .id("3")
                .idempotentId("123")
                .name("signup")
                .userId("abc")
                .sessionId(UUID.randomUUID().toString())
                .properties(j1)
                .tenantId("default")
                .timestamp(Instant.now())
                .createdAt(Instant.now())
                .build();

        Event event2 = Event.builder()
                .id("5")
                .idempotentId("1234")
                .name("signup")
                .userId("abc")
                .sessionId(UUID.randomUUID().toString())
                .properties(j2)
                .tenantId("default")
                .timestamp(Instant.now())
                .createdAt(Instant.now())
                .build();


        service.injectEvent(event).await().indefinitely();
//        service.injectEvent(event2).onItem().delayIt().by(Duration.ofSeconds(10)).await().indefinitely();

        List<Event> clickhouseEvents = Uni.createFrom().voidItem()
                .onItem().delayIt().by(Duration.ofSeconds(1))
                .onItem().transformToMulti(resp -> repository.runQuery(
                        String.format("select * from events"), Event.class
                )).collect().asList()
                .await().indefinitely();

        Log.info("\n\n\nRESPONSE : " + clickhouseEvents + "\n\n\n");
    }

}
