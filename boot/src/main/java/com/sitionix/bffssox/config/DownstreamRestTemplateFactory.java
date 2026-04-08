package com.sitionix.bffssox.config;

import lombok.RequiredArgsConstructor;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class DownstreamRestTemplateFactory {

    private final ApiRestClientDefaultsProps clientDefaultsProps;

    public RestTemplate create() {
        final RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(this.clientDefaultsProps.connectTimeout().toMillis()))
                .setResponseTimeout(Timeout.ofMilliseconds(this.clientDefaultsProps.readTimeout().toMillis()))
                .build();
        final HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(HttpClients.custom()
                        .setDefaultRequestConfig(requestConfig)
                        .build());

        return new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
    }
}
