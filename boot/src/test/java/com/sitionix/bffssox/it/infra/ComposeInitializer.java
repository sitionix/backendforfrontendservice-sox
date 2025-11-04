package com.sitionix.bffssox.it.infra;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;
import java.time.Duration;

public class ComposeInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final String SERVICE = "wiremock";
    private static final int CONTAINER_PORT = 18080;

    private static final DockerComposeContainer<?> compose =
            new DockerComposeContainer<>(new File("src/test/resources/compose/docker-compose.yml"))
                    .withExposedService(
                            SERVICE,
                            CONTAINER_PORT,
                            Wait.forHttp("/__admin").forStatusCode(200).withStartupTimeout(Duration.ofMinutes(2))
                    );

    @Override
    public void initialize(ConfigurableApplicationContext ctx) {
        boolean local = Boolean.parseBoolean(System.getenv("LOCAL_CONTAINERS"));
        if (!local) {
            compose.start();
            String host = compose.getServiceHost(SERVICE, CONTAINER_PORT);
            Integer port = compose.getServicePort(SERVICE, CONTAINER_PORT);

            TestPropertyValues.of(
                    "mock.http.host=" + host,
                    "mock.http.port=" + port,
                    "mock.http.base-url=http://" + host + ":" + port
            ).applyTo(ctx.getEnvironment());

            ctx.addApplicationListener(event -> {
                if (event instanceof ContextClosedEvent) {
                    try { compose.stop(); } catch (Exception ignored) {}
                }
            });
        }
    }
}
