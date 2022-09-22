//import io.quarkus.test.common.QuarkusTestResource;
//import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
//import org.testcontainers.containers.BindMode;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Objects;
//
//@QuarkusTestResource(ZookeeperTestResourceLifecycleManager.class)
//public class ClickhouseTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
//
//    private static final String CLICKHOUSE_DOCKER_IMAGE = System.getProperty("clickhouse.docker.image");
//    private static final String CLICKHOUSE_VERSION = System.getProperty("clickhouse.version");
//    private GenericContainer<?> clickhouse;
//
//    public Map<String, String> start() {
//
//        String clickhouseDockerImage;
//        String clickhouseVersion = "latest";
//        clickhouseDockerImage = Objects.requireNonNullElse(CLICKHOUSE_DOCKER_IMAGE, "clickhouse/clickhouse-server");
//
//        if (CLICKHOUSE_VERSION != null)
//            clickhouseVersion = CLICKHOUSE_VERSION;
//
//        clickhouse = new GenericContainer<>(clickhouseDockerImage + ":" + clickhouseVersion)
//                .withExposedPorts(8123, 9000)
//                .withFileSystemBind("./src/test/resources/clickhouse/config/clickhouse_config.xml", "/etc/clickhouse-server/config.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/clickhouse/config/clickhouse_metrika.xml", "/etc/clickhouse-server/metrika.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/clickhouse/config/macros/macros-01.xml", "/etc/clickhouse-server/config.d/macros.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/clickhouse/config/users.xml", "/etc/clickhouse-server/config.d/users.xml", BindMode.READ_ONLY)
//                .waitingFor(Wait.forHttp("/").forPort(8123));
//
//        clickhouse.start();
//
//        Map<String, String> conf = new HashMap<>();
//        conf.put("clickhouse.hosts", clickhouse.getHost() + ":" + clickhouse.getMappedPort(8123) + "?session_id=testSession");
//
//        return conf;
//
//    }
//
//    public void stop() {
//        clickhouse.stop();
//    }
//
//}
