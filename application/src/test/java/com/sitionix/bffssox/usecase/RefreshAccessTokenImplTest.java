package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshAccessTokenImplTest {

    private RefreshAccessToken refreshAccessToken;

    @Mock
    private AuthUserClient authUserClient;

    @BeforeEach
    void setUp() {
        refreshAccessToken = new RefreshAccessTokenImpl(this.authUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.authUserClient);
    }

    @Test
    void givenRefreshAccessTokenRequest_whenExecute_thenReturnRefreshAccessTokenResponse() {
        // given
        final RefreshAccessTokenRequest request = mock(RefreshAccessTokenRequest.class);
        final RefreshAccessTokenResponse response = mock(RefreshAccessTokenResponse.class);

        when(this.authUserClient.refreshAccessToken(request)).thenReturn(response);

        // when
        final RefreshAccessTokenResponse actual = this.refreshAccessToken.execute(request);

        // then
        assertThat(actual).isEqualTo(response);

        verify(this.authUserClient).refreshAccessToken(request);
    }
}
