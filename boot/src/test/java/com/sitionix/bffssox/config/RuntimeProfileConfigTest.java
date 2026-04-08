package com.sitionix.bffssox.config;

import com.sitionix.forge.security.client.config.ForgeSecurityClientProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import static org.assertj.core.api.Assertions.assertThat;

class RuntimeProfileConfigTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withInitializer(new ConfigDataApplicationContextInitializer());

    @Test
    void givenBaseApplicationConfiguration_whenContextStarts_thenEnvironmentSpecificKeysAreAbsentFromBaseConfig() {
        //given when then
        this.contextRunner
                .withPropertyValues(
                        "FORGE_SECURITY_DEV_JWT_SECRET=test-internal-auth-secret",
                        "forge.user-jwt.auth-base-url=http://placeholder.invalid/authsox"
                )
                .run(context -> {
                    final ConfigurableEnvironment environment = context.getEnvironment();
                    final List<String> applicationYamlKeys = this.applicationYamlKeys(environment);
                    final List<String> environmentSpecificKeys = applicationYamlKeys.stream()
                            .filter(this::isEnvironmentSpecificKey)
                            .toList();

                    assertThat(environment.getActiveProfiles()).isEmpty();
                    assertThat(environmentSpecificKeys).isEmpty();
                });
    }

    @Test
    void givenBaseApplicationConfiguration_whenContextStarts_thenResolveSharedOperationalDefaults() {
        //given when then
        this.contextRunner
                .withPropertyValues(
                        "FORGE_SECURITY_DEV_JWT_SECRET=test-internal-auth-secret",
                        "forge.user-jwt.auth-base-url=http://placeholder.invalid/authsox"
                )
                .run(context -> {
                    final ConfigurableEnvironment environment = context.getEnvironment();
                    final ApiRestClientDefaultsProps defaultsProps = Binder.get(environment)
                            .bind("api.rest.client.defaults", Bindable.of(ApiRestClientDefaultsProps.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind api.rest.client.defaults"));

                    assertThat(defaultsProps.connectTimeout()).isEqualTo(Duration.ofSeconds(2));
                    assertThat(defaultsProps.readTimeout()).isEqualTo(Duration.ofSeconds(10));
                    assertThat(environment.getProperty("management.endpoint.health.probes.enabled"))
                            .isEqualTo("true");
                    assertThat(environment.getProperty("management.health.livenessstate.enabled"))
                            .isEqualTo("true");
                    assertThat(environment.getProperty("management.health.readinessstate.enabled"))
                            .isEqualTo("true");
                    assertThat(environment.getProperty("management.endpoint.health.group.liveness.include"))
                            .isEqualTo("livenessState,ping");
                    assertThat(environment.getProperty("management.endpoint.health.group.readiness.include"))
                            .isEqualTo("readinessState,ping");
                });
    }

    @Test
    void givenLocalProfile_whenContextStarts_thenResolveLocalRuntimeTopology() {
        //given when then
        this.contextRunner
                .withPropertyValues(
                        "spring.profiles.active=local",
                        "FORGE_SECURITY_DEV_JWT_SECRET=test-internal-auth-secret"
                )
                .run(context -> {
                    final ConfigurableEnvironment environment = context.getEnvironment();
                    final CorsProps corsProps = Binder.get(environment)
                            .bind("sitionix.cors", Bindable.of(CorsProps.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind sitionix.cors"));
                    final ForgeSecurityClientProperties securityProperties = Binder.get(environment)
                            .bind("forge.security", Bindable.of(ForgeSecurityClientProperties.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind forge.security"));

                    assertThat(environment.getActiveProfiles()).containsExactly("local");
                    assertThat(environment.getProperty("api.rest.client.athssox.base-path"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(environment.getProperty("api.rest.client.stsssox.base-path"))
                            .isEqualTo("http://siteservice-sox:9080/stsssox");
                    assertThat(environment.getProperty("api.rest.client.wagssox.base-path"))
                            .isEqualTo("http://workspaceaggregationservice-sox:9082/wagssox");
                    assertThat(environment.getProperty("forge.user-jwt.auth-base-url"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(corsProps.allowedOrigins()).containsExactly(
                            "https://localhost:3000",
                            "https://localhost:3001",
                            "https://localhost:3002",
                            "https://localhost:3003"
                    );
                    assertThat(securityProperties.getTargets().get("sitionixAuth").getHost())
                            .isEqualTo("authorisationservice-sox");
                    assertThat(securityProperties.getTargets().get("sitionixSite").getHost())
                            .isEqualTo("siteservice-sox");
                    assertThat(securityProperties.getTargets().get("sitionixWorkspace").getHost())
                            .isEqualTo("workspaceaggregationservice-sox");
                    assertThat(securityProperties.getDev().getJwtSecret()).isEqualTo("test-internal-auth-secret");
                    assertThat(securityProperties.getDev().getIssuer()).isEqualTo("sitionix-internal");
                    assertThat(securityProperties.getDev().getTtlSeconds()).isEqualTo(300L);
                });
    }

    @Test
    void givenDevProfile_whenContextStarts_thenResolveDevRuntimeTopology() {
        //given when then
        this.contextRunner
                .withPropertyValues(
                        "spring.profiles.active=dev",
                        "FORGE_SECURITY_DEV_JWT_SECRET=test-internal-auth-secret"
                )
                .run(context -> {
                    final ConfigurableEnvironment environment = context.getEnvironment();
                    final CorsProps corsProps = Binder.get(environment)
                            .bind("sitionix.cors", Bindable.of(CorsProps.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind sitionix.cors"));
                    final ForgeSecurityClientProperties securityProperties = Binder.get(environment)
                            .bind("forge.security", Bindable.of(ForgeSecurityClientProperties.class))
                            .orElseThrow(() -> new IllegalStateException("Failed to bind forge.security"));

                    assertThat(environment.getActiveProfiles()).containsExactly("dev");
                    assertThat(environment.getProperty("api.rest.client.athssox.base-path"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(environment.getProperty("api.rest.client.stsssox.base-path"))
                            .isEqualTo("http://siteservice-sox:9080/stsssox");
                    assertThat(environment.getProperty("api.rest.client.wagssox.base-path"))
                            .isEqualTo("http://workspaceaggregationservice-sox:9082/wagssox");
                    assertThat(environment.getProperty("forge.user-jwt.auth-base-url"))
                            .isEqualTo("http://authorisationservice-sox:9090/authsox");
                    assertThat(corsProps.allowedOrigins()).containsExactly("https://app.dev.sitionix.com");
                    assertThat(securityProperties.getTargets().get("sitionixAuth").getHost())
                            .isEqualTo("authorisationservice-sox");
                    assertThat(securityProperties.getTargets().get("sitionixSite").getHost())
                            .isEqualTo("siteservice-sox");
                    assertThat(securityProperties.getTargets().get("sitionixWorkspace").getHost())
                            .isEqualTo("workspaceaggregationservice-sox");
                    assertThat(securityProperties.getDev().getJwtSecret()).isEqualTo("test-internal-auth-secret");
                    assertThat(securityProperties.getDev().getIssuer()).isEqualTo("sitionix-internal");
                    assertThat(securityProperties.getDev().getTtlSeconds()).isEqualTo(300L);
                });
    }

    private List<String> applicationYamlKeys(final ConfigurableEnvironment environment) {
        final List<String> propertyNames = new ArrayList<>();
        for (final PropertySource<?> propertySource : environment.getPropertySources()) {
            if (!(propertySource instanceof EnumerablePropertySource<?> enumerablePropertySource)) {
                continue;
            }
            if (!propertySource.getName().contains("application.yml")) {
                continue;
            }
            propertyNames.addAll(List.of(enumerablePropertySource.getPropertyNames()));
        }
        return propertyNames;
    }

    private boolean isEnvironmentSpecificKey(final String key) {
        if (Objects.isNull(key)) {
            return false;
        }
        return key.startsWith("sitionix.cors.allowed-origins")
                || key.equals("api.rest.client.athssox.base-path")
                || key.equals("api.rest.client.stsssox.base-path")
                || key.equals("api.rest.client.wagssox.base-path")
                || key.equals("forge.user-jwt.auth-base-url")
                || key.equals("forge.security.targets.sitionixAuth.host")
                || key.equals("forge.security.targets.sitionixSite.host")
                || key.equals("forge.security.targets.sitionixWorkspace.host");
    }
}
