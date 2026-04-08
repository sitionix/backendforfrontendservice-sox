package com.sitionix.bffssox.config;

import com.app_afesox.stsssox.client.api.SiteApi;
import com.app_afesox.stsssox.client.invoker.ApiClient;
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
@ConfigurationProperties(prefix = "api.rest.client.stsssox")
public class StsssoxApiConfig {

    private final DownstreamRestTemplateFactory downstreamRestTemplateFactory;
    private String basePath;

    @Bean("stsssoxClient")
    public ApiClient stsssoxClient(@Qualifier("stsssoxRestTemplate") final RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);

        apiClient.setBasePath(this.basePath);
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return apiClient;
    }

    @Bean("stsssoxRestTemplate")
    public RestTemplate stsssoxRestTemplate() {
        return this.downstreamRestTemplateFactory.create();
    }

    @Bean
    public SiteApi siteApi(@Qualifier("stsssoxClient") final ApiClient apiClient) {
        return new SiteApi(apiClient);
    }
}
