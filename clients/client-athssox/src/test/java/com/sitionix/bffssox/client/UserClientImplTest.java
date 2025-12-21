package com.sitionix.bffssox.client;

import com.app_afesox.athssox.client.api.UserApi;
import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import com.sitionix.bffssox.mapper.RegisterUserClientMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserClientImplTest {

    private UserClient userClient;

    @Mock
    private UserApi userApi;

    @Mock
    private RegisterUserClientMapper clientMapper;

    @Mock
    private ClientCallExecutor clientCallExecutor;

    @BeforeEach
    void setUp() {
        this.userClient = new UserClientImpl(this.userApi,
                this.clientMapper,
                this.clientCallExecutor);
    }

    @AfterEach
    void tearDown() {
        verifyNoMoreInteractions(this.clientMapper,
                this.userApi,
                this.clientCallExecutor);
    }

    @Test
    void givenRegisterUserRequest_whenRegisterUser_thenReturnRegisterUserResponse() {
        //given
        final RegisterUserRequest request = mock(RegisterUserRequest.class);
        final RegisterUserResponse response = mock(RegisterUserResponse.class);

        final RegisterUserDTO requestDTO = mock(RegisterUserDTO.class);
        final ResponseRegisterUserDTO responseDTO = mock(ResponseRegisterUserDTO.class);

        when(this.clientMapper.asRegisterUserDto(request)).thenReturn(requestDTO);
        when(this.clientMapper.asRegisterUserResponse(responseDTO)).thenReturn(response);
        when(this.clientCallExecutor.execute(any())).thenAnswer(invocation -> {
            final Supplier<ResponseRegisterUserDTO> supplier = invocation.getArgument(0);
            return supplier.get();
        });
        when(this.userApi.registerUser(requestDTO)).thenReturn(responseDTO);

        //when
        final RegisterUserResponse actual = this.userClient.registerUser(request);

        //then
        assertThat(actual).isEqualTo(response);

        verify(this.clientMapper).asRegisterUserDto(request);
        verify(this.clientMapper).asRegisterUserResponse(responseDTO);
        verify(this.clientCallExecutor).execute(any());
        verify(this.userApi).registerUser(requestDTO);
    }
}
