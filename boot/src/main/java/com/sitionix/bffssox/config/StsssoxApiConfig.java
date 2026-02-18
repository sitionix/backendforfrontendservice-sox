package com.sitionix.bffssox.config;

import com.app_afesox.stsssox.client.api.SiteApi;
import com.app_afesox.stsssox.client.invoker.ApiClient;
import lombok.Data;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Data
@Configuration
@ConfigurationProperties(prefix = "api.rest.client.stsssox")
public class StsssoxApiConfig {

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
        final HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
        return new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
    }

    @Bean
    public SiteApi siteApi(@Qualifier("stsssoxClient") final ApiClient apiClient) {
        return new SiteApi(apiClient);
    }
}
