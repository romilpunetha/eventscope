//import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
//import io.smallrye.reactive.messaging.providers.connectors.InMemoryConnector;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//public class KafkaTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
//
//
//    @Override
//    public Map<String, String> start() {
//
//        Map<String, String> props1 = InMemoryConnector.switchOutgoingChannelsToInMemory(Schema.KAFKA_CHANNEL_NAME_EVENTS);
//        return new HashMap<>(props1);
//    }
//
//    @Override
//    public void stop() {
//
//        InMemoryConnector.clear();
//    }
//
//}
