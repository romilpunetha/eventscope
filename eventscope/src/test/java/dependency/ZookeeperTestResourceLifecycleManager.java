//import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
//import lombok.SneakyThrows;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//import util.XMLParser;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//public class ZookeeperTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
//
//    private static final String ZOOKEEPER_DOCKER_IMAGE = System.getProperty("zookeeper.docker.image");
//    private static final String ZOOKEEPER_VERSION = System.getProperty("zookeeper.version");
//    private GenericContainer<?> zookeeper;
//
//    @SneakyThrows
//    public Map<String, String> start() {
//
//        String zookeeperDockerImage;
//        String zookeeperVersion = "latest";
//        zookeeperDockerImage = Objects.requireNonNullElse(ZOOKEEPER_DOCKER_IMAGE, "zookeeper");
//
//        if (ZOOKEEPER_VERSION != null)
//            zookeeperVersion = ZOOKEEPER_VERSION;
//
//        zookeeper = new GenericContainer<>(zookeeperDockerImage + ":" + zookeeperVersion)
//                .withExposedPorts(2181, 2182)
//                .waitingFor(Wait.forLogMessage(".*ZooKeeper audit is disabled.*", 1));
//
//        zookeeper.start();
//
//        XMLParser xmlParser = new XMLParser();
//        xmlParser.updateData("./src/test/resources/clickhouse/config/clickhouse_metrika.xml",
//                "port",
//                1,
//                String.valueOf(zookeeper.getMappedPort(2181)));
//
//        xmlParser.updateData("./src/test/resources/clickhouse/config/clickhouse_metrika.xml",
//                "host",
//                1,
//                zookeeper.getHost());
//
//        Map<String, String> conf = new HashMap<>();
//        conf.put("zookeeper.hosts", zookeeper.getHost() + ":" + zookeeper.getMappedPort(2181));
//        return conf;
//
//    }
//
//    public void stop() {
//        zookeeper.stop();
//    }
//
//}
