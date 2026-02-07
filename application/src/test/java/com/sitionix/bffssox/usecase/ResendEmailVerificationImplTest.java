package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResendEmailVerificationImplTest {

    private ResendEmailVerification resendEmailVerification;

    @Mock
    private AuthUserClient authUserClient;

    @BeforeEach
    void setUp() {
        this.resendEmailVerification = new ResendEmailVerificationImpl(this.authUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.authUserClient);
    }

    @Test
    void givenResendEmailVerificationResponse_whenExecute_thenReturnResendEmailVerificationResponse() {
        //given
        final ResendEmailVerificationResponse response = mock(ResendEmailVerificationResponse.class);

        when(this.authUserClient.resendEmailVerification()).thenReturn(response);

        //when
        final ResendEmailVerificationResponse actual = this.resendEmailVerification.execute();

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.authUserClient).resendEmailVerification();
    }
}
