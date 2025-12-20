package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import com.sitionix.bffssox.domain.RegisterUserRole;
import com.sitionix.bffssox.domain.RegisterUserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RegisterUserApiMapperTest {

    private RegisterUserApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new RegisterUserApiMapperImpl();
    }

    @Test
    void givenRegisterUserDto_whenAsRegisterUserRequest_thenReturnRegisterUserRequest() {
        //given
        final UUID uuid = UUID.randomUUID();
        final RegisterUserRequest expected = this.registerUserRequest(uuid);
        final RegisterUserDTO given = this.registerUserDTO(uuid);

        //when
        final RegisterUserRequest actual = this.mapper.asRegisterUserRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenRegisterUserResponse_whenAsResponseRegisterUserDto_thenReturnResponseRegisterUserDto() {
        //given
        final ResponseRegisterUserDTO expected = this.responseRegisterUserDTO();
        final RegisterUserResponse given = this.registerUserResponse();

        //when
        final ResponseRegisterUserDTO actual = this.mapper.asResponseRegisterUserDto(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private RegisterUserDTO registerUserDTO(final UUID uuid) {
        return RegisterUserDTO.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .role(RegisterUserDTO.RoleEnum.SITE_ADMIN)
                .build();
    }

    private RegisterUserRequest registerUserRequest(final UUID uuid) {
        return RegisterUserRequest.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .role(RegisterUserRole.SITE_ADMIN)
                .build();
    }

    private ResponseRegisterUserDTO responseRegisterUserDTO() {
        return ResponseRegisterUserDTO.builder()
                .message("message")
                .status(ResponseRegisterUserDTO.StatusEnum.ACTIVE)
                .userId(10L)
                .build();
    }

    private RegisterUserResponse registerUserResponse() {
        return RegisterUserResponse.builder()
                .message("message")
                .status(RegisterUserStatus.ACTIVE)
                .userId(10L)
                .build();
    }
}
