package com.sitionix.bffssox.config;

import com.app_afesox.athssox.client.api.AuthApi;
import com.app_afesox.athssox.client.api.UserApi;
import com.app_afesox.athssox.client.invoker.ApiClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "api.rest.athssox")
public class AthssoxApiConfig {

    private String basePath;

    @Bean("athssoxClient")
    public ApiClient athssoxClient() {

        final ApiClient apiClient = new ApiClient();

        apiClient.setBasePath(this.basePath);

        return apiClient;
    }

    @Bean
    public AuthApi userApi(@Qualifier("athssoxClient") final ApiClient apiClient) {
        return new AuthApi(apiClient);
    }

}
