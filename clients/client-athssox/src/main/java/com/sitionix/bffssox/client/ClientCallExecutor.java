package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.ClientResponseException;
import java.util.Collections;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;

@Component
public class ClientCallExecutor {

    public <T> T execute(final Supplier<T> call) {
        try {
            return call.get();
        } catch (HttpStatusCodeException ex) {
            throw new ClientResponseException(
                    ex.getStatusCode().value(),
                    ex.getResponseBodyAsString(),
                    ex.getResponseHeaders(),
                    ex
            );
        } catch (ResourceAccessException ex) {
            throw new ClientResponseException(
                    502,
                    "{\"code\":502,\"title\":\"upstream_error\",\"details\":\"Upstream service unavailable\"}",
                    Collections.emptyMap(),
                    ex
            );
        }
    }
}
