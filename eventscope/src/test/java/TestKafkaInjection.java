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
import io.smallrye.mutiny.Uni;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.inject.Inject;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@QuarkusTestResource(ClickhouseTestResourceLifecycleManager.class)
//@QuarkusTestResource(KafkaTestResourceLifecycleManager.class)
public class TestKafkaInjection {

    Table eventsTable;
    Table eventsKafkaTable;

    View kafkaToEventsView;

    String eventsTopic = "eventscope.events";
    String testGroup = "test.group";

//    @Inject
//    @Connector(value = "smallrye-in-memory")
//    InMemoryConnector connector;

    @Inject
    ClickhouseRepository repository;
    @Inject
    EventScopeService service;
    ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    public void init() {

        String clusterName = "cluster_1";

        String kafkaBrokers = "clickhouse-kafka:9092";

//        String kafkaBrokers = ConfigProvider.getConfig().getValue("kafka.bootstrap.servers", String.class);

        this.eventsTable = new EventsTable(clusterName);

        KafkaEngine kafkaEngine = new KafkaEngine(kafkaBrokers, eventsTopic, testGroup, InputFormat.JSONAsString);
        this.eventsKafkaTable = new EventsKafkaTable(clusterName, kafkaEngine);

        kafkaToEventsView = new KafkaToEventsMaterializedView(clusterName);

        List<Object> clusters = repository.runQuery("show clusters;").collect().asList().await().indefinitely();


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

//        InMemorySink<String> eventsIn = connector.sink(eventsTopic);

        String newString = "{\"a\" : \"b\", \"c\" : \"e\"}";
        JsonNode newNode = mapper.readTree(newString);

        Event event = Event.builder()
                .id("5")
                .idempotentId("123")
                .properties(newNode)
                .timeStamp(Instant.now())
                .build();

//        service.injectEvent(event).await().indefinitely();

//        await().<List<? extends Message<String>>>until(eventsIn::received, t -> t.size() == 1);

        List<Event> clickhouseEvents = Uni.createFrom().voidItem()
                .onItem().delayIt().by(Duration.ofSeconds(1))
                .onItem().transformToMulti(resp -> repository.runQuery(
                        String.format("select * from events"), Event.class
                )).collect().asList()
                .await().indefinitely();

        Log.info("\n\n\nRESPONSE : " + clickhouseEvents.get(0) + "\n\n\n");
    }

}
