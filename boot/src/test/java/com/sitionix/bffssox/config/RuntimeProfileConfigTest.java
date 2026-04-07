package com.sitionix.bffssox.config;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.core.env.ConfigurableEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

class RuntimeProfileConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer());

    @Test
    void givenBaseConfiguration_whenContextStarts_thenResolveLocalDefaults() {
        //given when
        this.contextRunner.run(context -> {
            final ConfigurableEnvironment environment = context.getEnvironment();
            final CorsProps corsProps = Binder.get(environment)
                    .bind("sitionix.cors", Bindable.of(CorsProps.class))
                    .orElseThrow(() -> new IllegalStateException("Failed to bind sitionix.cors"));

            //then
            assertThat(environment.getActiveProfiles()).isEmpty();
            assertThat(corsProps.allowedOrigins()).containsExactly("https://localhost:3000");
            assertThat(environment.getProperty("api.rest.client.athssox.base-path"))
                    .isEqualTo("http://localhost:9090/authsox");
        });
    }

    @Test
    void givenDevProfile_whenContextStarts_thenResolveDevRuntimeTopology() {
        //given when
        this.contextRunner
                .withPropertyValues("spring.profiles.active=dev")
                .run(context -> {
                    final ConfigurableEnvironment environment = context.getEnvironment();
                    final CorsProps corsProps = Binder.get(environment)
                            .bind("sitionix.cors", Bindable.of(CorsProps.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind sitionix.cors"));

                    //then
                    assertThat(environment.getActiveProfiles()).containsExactly("dev");
                    assertThat(corsProps.allowedOrigins()).containsExactly(
                            "https://app.dev.sitionix.com",
                            "https://auth.dev.sitionix.com",
                            "https://workspace.dev.sitionix.com",
                            "https://builder.dev.sitionix.com"
                    );
                    assertThat(environment.getProperty("api.rest.client.athssox.base-path"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(environment.getProperty("api.rest.client.stsssox.base-path"))
                            .isEqualTo("http://siteservice-sox:9080/stsssox");
                    assertThat(environment.getProperty("api.rest.client.wagssox.base-path"))
                            .isEqualTo("http://workspaceaggregationservice-sox:9082/wagssox");
                    assertThat(environment.getProperty("forge.user-jwt.auth-base-url"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(environment.getProperty("forge.security.targets.sitionixAuth.host"))
                            .isEqualTo("authorisationservice-sox");
                    assertThat(environment.getProperty("forge.security.targets.sitionixSite.host"))
                            .isEqualTo("siteservice-sox");
                    assertThat(environment.getProperty("forge.security.targets.sitionixWorkspace.host"))
                            .isEqualTo("workspaceaggregationservice-sox");
                });
    }
}
