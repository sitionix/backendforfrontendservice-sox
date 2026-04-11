package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.ClientResponseException;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
public class AtmssoxClientCallExecutor {

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
        }
    }
}
