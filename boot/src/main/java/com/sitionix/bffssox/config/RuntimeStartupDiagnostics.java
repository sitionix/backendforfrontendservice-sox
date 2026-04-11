package com.sitionix.bffssox.config;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RuntimeStartupDiagnostics {

    private final Environment environment;
    private final CorsProps corsProps;
    private final AthssoxApiConfig athssoxApiConfig;
    private final AtmssoxApiConfig atmssoxApiConfig;
    private final StsssoxApiConfig stsssoxApiConfig;
    private final WagssoxApiConfig wagssoxApiConfig;
    private final ApiRestClientDefaultsProps clientDefaultsProps;

    @EventListener(ApplicationReadyEvent.class)
    public void logRuntimeContract() {
        final List<String> activeProfiles = Arrays.asList(this.environment.getActiveProfiles());
        final String contextPath = this.environment.getProperty("server.servlet.context-path", "");
        final String readinessEndpoint = contextPath + "/actuator/health/readiness";
        final String livenessEndpoint = contextPath + "/actuator/health/liveness";

        log.info(
                "BFF ready: profiles={}, contextPath={}, readinessEndpoint={} (shallow), livenessEndpoint={}, "
                        + "corsOrigins={}",
                activeProfiles,
                contextPath,
                readinessEndpoint,
                livenessEndpoint,
                this.corsProps.allowedOrigins()
        );
        log.info(
                "BFF downstream contract: authBasePath={}, automationBasePath={}, siteBasePath={}, workspaceBasePath={}, "
                        + "connectTimeout={}, readTimeout={}",
                this.athssoxApiConfig.getBasePath(),
                this.atmssoxApiConfig.getBasePath(),
                this.stsssoxApiConfig.getBasePath(),
                this.wagssoxApiConfig.getBasePath(),
                this.clientDefaultsProps.connectTimeout(),
                this.clientDefaultsProps.readTimeout()
        );
    }
}
