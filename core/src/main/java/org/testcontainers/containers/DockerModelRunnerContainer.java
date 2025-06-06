package org.testcontainers.containers;

import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

/**
 * Testcontainers proxy container for the Docker Model Runner service
 * provided by Docker Desktop.
 * <p>
 * Supported images: {@code alpine/socat}
 * <p>
 * Exposed ports: 80
 */
public class DockerModelRunnerContainer extends SocatContainer {

    private static final String MODEL_RUNNER_ENDPOINT = "model-runner.docker.internal";

    private static final int PORT = 80;

    public DockerModelRunnerContainer(String image) {
        this(DockerImageName.parse(image));
    }

    public DockerModelRunnerContainer(DockerImageName image) {
        super(image);
        withTarget(PORT, MODEL_RUNNER_ENDPOINT);
        waitingFor(Wait.forHttp("/").forResponsePredicate(res -> res.contains("The service is running")));
    }

    public String getBaseEndpoint() {
        return "http://" + getHost() + ":" + getMappedPort(PORT);
    }

    public String getOpenAIEndpoint() {
        return getBaseEndpoint() + "/engines";
    }
}
