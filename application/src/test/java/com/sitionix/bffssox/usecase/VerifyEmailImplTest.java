package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerifyEmailImplTest {

    private VerifyEmail verifyEmail;

    @Mock
    private AuthUserClient authUserClient;

    @BeforeEach
    void setUp() {
        verifyEmail = new VerifyEmailImpl(this.authUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.authUserClient);
    }

    @Test
    void givenEmailVerificationRequest_whenExecute_thenReturnEmailVerificationResponse() {
        // given
        final EmailVerificationRequest request = mock(EmailVerificationRequest.class);
        final EmailVerificationResponse response = mock(EmailVerificationResponse.class);

        when(this.authUserClient.verifyEmail(request)).thenReturn(response);

        // when
        final EmailVerificationResponse actual = this.verifyEmail.execute(request);

        // then
        assertThat(actual).isEqualTo(response);

        verify(this.authUserClient).verifyEmail(request);
    }
}
