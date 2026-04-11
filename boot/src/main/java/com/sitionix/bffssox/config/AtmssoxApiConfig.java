package com.sitionix.bffssox.config;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.invoker.ApiClient;
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
@ConfigurationProperties(prefix = "api.rest.client.atmssox")
public class AtmssoxApiConfig {

    private final DownstreamRestTemplateFactory downstreamRestTemplateFactory;

    private String basePath;

    @Bean("atmssoxClient")
    public ApiClient atmssoxClient(@Qualifier("atmssoxRestTemplate") final RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);
        apiClient.setBasePath(this.basePath);
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return apiClient;
    }

    @Bean("atmssoxRestTemplate")
    public RestTemplate atmssoxRestTemplate() {
        return this.downstreamRestTemplateFactory.create();
    }

    @Bean
    public AgentApi agentApi(@Qualifier("atmssoxClient") final ApiClient apiClient) {
        return new AgentApi(apiClient);
    }
}
