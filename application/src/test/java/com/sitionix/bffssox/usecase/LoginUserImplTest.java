package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.BffLoginSessionResult;
import com.sitionix.bffssox.domain.BffSessionManager;
import com.sitionix.bffssox.domain.LoginRequest;
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
    private BffSessionManager bffSessionManager;

    @BeforeEach
    void setUp() {
        this.loginUser = new LoginUserImpl(this.bffSessionManager);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.bffSessionManager);
    }

    @Test
    void givenLoginRequest_whenExecute_thenReturnLoginSessionResult() {
        //given
        final LoginRequest loginRequest = mock(LoginRequest.class);
        final BffLoginSessionResult loginSessionResult = mock(BffLoginSessionResult.class);

        when(this.bffSessionManager.createSession(loginRequest, "Mozilla", "10.0.0.5"))
                .thenReturn(loginSessionResult);

        //when
        final BffLoginSessionResult actual = this.loginUser.execute(loginRequest, "Mozilla", "10.0.0.5");

        //then
        assertThat(actual).isEqualTo(loginSessionResult);

        verify(this.bffSessionManager).createSession(loginRequest, "Mozilla", "10.0.0.5");
    }
}
