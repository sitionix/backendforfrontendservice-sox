package com.sitionix.bffssox.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Data
@ConfigurationProperties(prefix = "security.session")
public class BffSessionProperties {

    private String cookieName = "__Host-admin_session";

    private String sameSite = "Lax";

    private boolean secure = true;

    private boolean httpOnly = true;

    private long idleTimeoutSeconds = 86_400L;

    private long absoluteTimeoutSeconds = 1_209_600L;

    private long rotationIntervalSeconds = 86_400L;

    private long refreshSkewSeconds = 60L;

    private long touchThrottleSeconds = 30L;

    private final List<String> publicPaths = new ArrayList<>(List.of(
            "/api/v1/session",
            "/api/v1/auth/login",
            "/api/v1/auth/refresh",
            "/api/v1/users",
            "/api/v1/auth/email/verify"
    ));

    private Csrf csrf = new Csrf();

    private SessionStore sessionStore = new SessionStore();

    @Data
    public static class Csrf {
        private boolean enabled = true;

        private String cookieName = "XSRF-TOKEN";

        private String headerName = "X-CSRF";

        private List<String> protectedMethods = new ArrayList<>(List.of("POST", "PUT", "PATCH", "DELETE"));

        private List<String> ignorePaths = new ArrayList<>(List.of(
                "/api/v1/auth/login",
                "/api/v1/users",
                "/api/v1/auth/email/verify",
                "/api/v1/auth/email/verify/resend"
        ));
    }

    @Data
    public static class SessionStore {
        private String type = "inmemory";

        private Redis redis = new Redis();
    }

    @Data
    public static class Redis {
        private String host = "localhost";

        private int port = 6379;
    }
}
