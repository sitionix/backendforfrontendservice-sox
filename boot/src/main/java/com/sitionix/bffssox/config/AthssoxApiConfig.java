package com.sitionix.bffssox.config;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.api.UserApi;
import com.app_afesox.athssox.client.invoker.ApiClient;
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
@ConfigurationProperties(prefix = "api.rest.client.athssox")
public class AthssoxApiConfig {

    private final DownstreamRestTemplateFactory downstreamRestTemplateFactory;
    private String basePath;

    @Bean("athssoxClient")
    public ApiClient athssoxClient(@Qualifier("athssoxRestTemplate") final RestTemplate restTemplate) {
        final ApiClient apiClient = new ApiClient(restTemplate);

        apiClient.setBasePath(this.basePath);
        apiClient.addDefaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        apiClient.addDefaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return apiClient;
    }

    @Bean("athssoxRestTemplate")
    public RestTemplate athssoxRestTemplate() {
        return this.downstreamRestTemplateFactory.create();
    }

    @Bean
    public AuthApi authApi(@Qualifier("athssoxClient") final ApiClient apiClient) {
        return new AuthApi(apiClient);
    }

    @Bean
    public UserApi userApi(@Qualifier("athssoxClient") final ApiClient apiClient) {
        return new UserApi(apiClient);
    }

}
