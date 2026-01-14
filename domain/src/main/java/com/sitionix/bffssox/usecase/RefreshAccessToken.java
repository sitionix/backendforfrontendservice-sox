package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;

public interface RefreshAccessToken {

    RefreshAccessTokenResponse execute(RefreshAccessTokenRequest request);
}
