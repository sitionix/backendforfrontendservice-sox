package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.UserAccessTokenContext;
import com.sitionix.bffssox.domain.UserAccessTokenHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserAccessTokenForwardingInterceptor implements ClientHttpRequestInterceptor {

    private final UserAccessTokenContext userAccessTokenContext;

    @Override
    public ClientHttpResponse intercept(final HttpRequest request,
                                        final byte[] body,
                                        final ClientHttpRequestExecution execution) throws IOException {
        final String accessToken = this.userAccessTokenContext.get();
        if (StringUtils.hasText(accessToken)) {
            final HttpHeaders headers = request.getHeaders();
            if (!headers.containsKey(UserAccessTokenHeaders.USER_AUTHORIZATION)) {
                headers.set(UserAccessTokenHeaders.USER_AUTHORIZATION, accessToken);
            }
        }
        return execution.execute(request, body);
    }
}
