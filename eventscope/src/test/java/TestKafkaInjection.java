import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.eventscope.api.Event;
import io.eventscope.api.EventScopeService;
import io.eventscope.clickhouse.ClickhouseRepository;
import io.eventscope.clickhouse.engine.InputFormat;
import io.eventscope.clickhouse.engine.integration.kafka.KafkaEngine;
import io.eventscope.clickhouse.table.EventsKafkaTable;
import io.eventscope.clickhouse.table.EventsTable;
import io.eventscope.clickhouse.table.Table;
import io.eventscope.clickhouse.view.View;
import io.eventscope.clickhouse.view.materializedview.KafkaToEventsMaterializedView;
import io.quarkus.logging.Log;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestKafkaInjection {

    Table eventsTable;
    Table eventsKafkaTable;

    View kafkaToEventsView;

    String testTopic = "test.topic";
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

        KafkaEngine kafkaEngine = new KafkaEngine(kafkaBrokers, "eventscope.events", testGroup, InputFormat.JSONAsString);
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
    public void addEventToClickhouse() throws JsonProcessingException {

        String newString = "{\"a\" : \"b\", \"c\" : \"e\"}";
        JsonNode newNode = mapper.readTree(newString);

        Event event = Event.builder()
                .id("3")
                .idempotentId("123")
                .properties(newNode)
                .timeStamp(Instant.now())
                .build();

        service.injectEvent(event).await().indefinitely();

        List<String> clickhouseEvent = repository.<String>runQuery(
                        String.format("select * from events")
                ).collect().asList()
                .await().indefinitely();

        Log.info("\n\n\nRESPONSE : " + clickhouseEvent + "\n\n\n");
    }

}
