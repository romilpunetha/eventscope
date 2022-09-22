//import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
//import org.apache.commons.lang3.StringUtils;
//import org.testcontainers.containers.BindMode;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class ClickhouseTestResourceLifecycleManager implements QuarkusTestResourceLifecycleManager {
//
//    private static final String CLICKHOUSE_DOCKER_IMAGE = System.getProperty("clickhouse.docker.image");
//    private static final String CLICKHOUSE_VERSION = System.getProperty("clickhouse.version");
//
//    private static final String CLICKHOUSE_HOSTS = System.getProperty("clickhouse.hosts");
//
//    private GenericContainer<?> clickhouse;
//
//    public Map<String, String> start() {
//
//        String clickhouseDockerImage;
//        String clickhouseVersion = "latest";
//        if (CLICKHOUSE_DOCKER_IMAGE != null)
//            clickhouseDockerImage = CLICKHOUSE_DOCKER_IMAGE;
//        else
//            clickhouseDockerImage = "clickhouse/clickhouse-server";
//
//        if (CLICKHOUSE_VERSION != null)
//            clickhouseVersion = CLICKHOUSE_VERSION;
//
//        clickhouse = new GenericContainer<>(clickhouseDockerImage + ":" + clickhouseVersion)
//                .withExposedPorts(8123, 9000)
//                .withFileSystemBind("./src/test/resources/config/clickhouse_config.xml", "/etc/clickhouse-server/config.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/config/clickhouse_metrika.xml", "/etc/clickhouse-server/metrika.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/config/macros/macros-01.xml", "/etc/clickhouse-server/config.d/macros.xml", BindMode.READ_ONLY)
//                .withFileSystemBind("./src/test/resources/config/users.xml", "/etc/clickhouse-server/config.d/users.xml", BindMode.READ_ONLY)
//                .waitingFor(Wait.forHttp("/").forPort(8123));
//
//        clickhouse.start();
//
//        Map<String, String> conf = new HashMap<>();
//        conf.put("clickhouse.hosts", (StringUtils.isEmpty(CLICKHOUSE_HOSTS) ? "http://localhost:" + clickhouse.getMappedPort(8123) : CLICKHOUSE_HOSTS) + "?session_id=testSession");
//
//        return conf;
//
//    }
//
//    public void stop() {
//        clickhouse.stop();
//    }
//}
