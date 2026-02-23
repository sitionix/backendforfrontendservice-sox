package com.sitionix.bffssox.security;

import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSessionContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BffUserTokenPropagationInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
        final BffResolvedSession session = BffSessionContextHolder.get();
        if (session != null && session.getSession() != null && session.getSession().getAccessToken() != null) {
            request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + session.getSession().getAccessToken());
            request.getHeaders().set("X-Forge-User-Sub", session.getSession().getUserId());
        }
        return execution.execute(request, body);
    }
}
