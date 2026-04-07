package com.sitionix.bffssox.config;

import com.app_afesox.wagssox.client.api.SiteApi;
import com.app_afesox.wagssox.client.invoker.ApiClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

@Getter
@Setter
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "api.rest.client.wagssox")
public class WagssoxApiConfig {

    private final DownstreamRestTemplateFactory downstreamRestTemplateFactory;
    private String basePath;

    @Bean("wagssoxClient")
    public ApiClient wagssoxClient(@Qualifier("wagssoxRestTemplate") final RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);

        apiClient.setBasePath(this.basePath);
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return apiClient;
    }

    @Bean("wagssoxRestTemplate")
    public RestTemplate wagssoxRestTemplate() {
        return this.downstreamRestTemplateFactory.create();
    }

    @Bean
    public SiteApi wagssoxSiteApi(@Qualifier("wagssoxClient") final ApiClient apiClient) {
        return new SiteApi(apiClient);
    }
}
