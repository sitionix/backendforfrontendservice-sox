package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.UserClient;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegisterUserImplTest {

    private RegisterUser registerUser;

    @Mock
    private UserClient userClient;

    @BeforeEach
    void setUp() {
        registerUser = new RegisterUserImpl(this.userClient);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.userClient);
    }

    @Test
    void givenRegisterUserRequest_whenExecute_thenReturnRegisterUserResponse() {
        //given
        final RegisterUserRequest registerUserRequest = mock(RegisterUserRequest.class);
        final RegisterUserResponse registerUserResponse = mock(RegisterUserResponse.class);

        when(this.userClient.registerUser(registerUserRequest)).thenReturn(registerUserResponse);

        //when
        final RegisterUserResponse actual = this.registerUser.execute(registerUserRequest);

        //then
        assertThat(actual).isEqualTo(registerUserResponse);

        verify(this.userClient).registerUser(registerUserRequest);
    }
}
