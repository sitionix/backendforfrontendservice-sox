package com.sitionix.bffssox.it.infra.utils.wiremock.domain.check;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Pattern;

public final class PathTemplate {

    private static final Pattern PLACEHOLDER = Pattern.compile("\\{([^/}]+)}");

    private PathTemplate() {}

    public static String resolve(String template, Map<String, ?> vars) {
        if (template == null) {
            throw new IllegalArgumentException("template is null");
        }

        if (vars != null && !vars.isEmpty()) {
            for (final Map.Entry<String, ?> e : vars.entrySet()) {
                final String token = "{" + e.getKey() + "}";
                final String value = URLEncoder.encode(String.valueOf(e.getValue()), StandardCharsets.UTF_8);
                template = template.replace(token, value);
            }
        }

        if (PLACEHOLDER.matcher(template).find()) {
            throw new IllegalArgumentException("Unresolved template variables in: " + template);
        }

        return template;
    }
}
