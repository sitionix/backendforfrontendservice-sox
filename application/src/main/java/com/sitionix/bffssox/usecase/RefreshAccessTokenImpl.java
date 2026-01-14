package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshAccessTokenImpl implements RefreshAccessToken {

    private final AuthUserClient authUserClient;

    @Override
    public RefreshAccessTokenResponse execute(final RefreshAccessTokenRequest request) {
        return this.authUserClient.refreshAccessToken(request);
    }
}
