package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AuthUserClient;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginUserImplTest {

    private LoginUser loginUser;

    @Mock
    private AuthUserClient authUserClient;

    @BeforeEach
    void setUp() {
        loginUser = new LoginUserImpl(this.authUserClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.authUserClient);
    }

    @Test
    void givenLoginRequest_whenExecute_thenReturnLoginResponse() {
        // given
        final LoginRequest loginRequest = mock(LoginRequest.class);
        final LoginResponse loginResponse = mock(LoginResponse.class);

        when(this.authUserClient.login(loginRequest)).thenReturn(loginResponse);

        // when
        final LoginResponse actual = this.loginUser.execute(loginRequest);

        // then
        assertThat(actual).isEqualTo(loginResponse);

        verify(this.authUserClient).login(loginRequest);

    }
}