package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.BffResolvedSession;
import com.sitionix.bffssox.domain.BffSessionContextHolder;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.ClientResponseException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import java.util.function.Supplier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;

@Component
@RequiredArgsConstructor
public class StsssoxClientCallExecutor {

    private final BffSessionManager bffSessionManager;

    public <T> T execute(final Supplier<T> call) {
        try {
            return call.get();
        } catch (HttpStatusCodeException ex) {
            if (ex.getStatusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                final BffResolvedSession resolvedSession = BffSessionContextHolder.get();
                if (resolvedSession != null) {
                    final BffResolvedSession refreshedSession = this.bffSessionManager
                            .refreshSession(resolvedSession.getSessionId(), true)
                            .orElse(null);
                    if (refreshedSession != null) {
                        BffSessionContextHolder.set(refreshedSession);
                        try {
                            return call.get();
                        } catch (HttpStatusCodeException retryEx) {
                            throw new ClientResponseException(
                                    retryEx.getStatusCode().value(),
                                    retryEx.getResponseBodyAsString(),
                                    retryEx.getResponseHeaders(),
                                    retryEx
                            );
                        }
                    }
                }
            }
            throw new ClientResponseException(
                    ex.getStatusCode().value(),
                    ex.getResponseBodyAsString(),
                    ex.getResponseHeaders(),
                    ex
            );
        }
    }
}
