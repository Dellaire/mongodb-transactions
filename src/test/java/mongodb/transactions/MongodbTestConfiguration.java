package mongodb.transactions;

import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

public class MongodbTestConfiguration {

    private final static String SKIP_TESTCONTAINERS_OPTION = "skipTestcontainers";
    private final static String MONGODB_VERSION = "mongo:6.0.3";
    private final static String MONGODB_PORT = "spring.data.mongodb.port";

    public static void startMongodb() {

        if (System.getProperty(SKIP_TESTCONTAINERS_OPTION) == null) {

            MongoDBContainer mongoDBContainer =
                    new MongoDBContainer(DockerImageName.parse(MONGODB_VERSION)).withExposedPorts(27017);
            mongoDBContainer.start();
            System.setProperty(MONGODB_PORT, mongoDBContainer.getFirstMappedPort().toString());
        }
    }
}
