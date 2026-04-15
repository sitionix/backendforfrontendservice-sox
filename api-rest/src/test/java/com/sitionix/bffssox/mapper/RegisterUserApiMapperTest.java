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

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final RegisterUserDTO registerUserDTO = null;
        final RegisterUserResponse registerUserResponse = null;

        //when
        final RegisterUserRequest actualRequest = this.mapper.asRegisterUserRequest(registerUserDTO);
        final ResponseRegisterUserDTO actualResponse = this.mapper.asResponseRegisterUserDto(registerUserResponse);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    @Test
    void givenAllRoleAndStatusVariants_whenMap_thenReturnMappedVariants() {
        //given
        final RegisterUserDTO request = RegisterUserDTO.builder()
                .email("e")
                .password("p")
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .role(RegisterUserDTO.RoleEnum.ECOSYSTEM_OWNER)
                .build();
        final RegisterUserResponse response = RegisterUserResponse.builder()
                .message("m")
                .status(RegisterUserStatus.BANNED)
                .userId(1L)
                .build();

        //when
        final RegisterUserRequest actualRequest = this.mapper.asRegisterUserRequest(request);
        final ResponseRegisterUserDTO actualResponse = this.mapper.asResponseRegisterUserDto(response);

        //then
        assertThat(actualRequest.getRole()).isEqualTo(RegisterUserRole.ECOSYSTEM_OWNER);
        assertThat(actualResponse.getStatus()).isEqualTo(ResponseRegisterUserDTO.StatusEnum.BANNED);
    }

    @Test
    void givenAllEnums_whenMap_thenReturnMappedEnums() {
        //given
        final UUID siteId = UUID.fromString("00000000-0000-0000-0000-000000000005");

        //when
        for (final RegisterUserDTO.RoleEnum roleEnum : RegisterUserDTO.RoleEnum.values()) {
            final RegisterUserDTO request = RegisterUserDTO.builder()
                    .email("e")
                    .password("p")
                    .siteId(siteId)
                    .role(roleEnum)
                    .build();
            final RegisterUserRequest actualRequest = this.mapper.asRegisterUserRequest(request);
            assertThat(actualRequest.getRole().name()).isEqualTo(roleEnum.name());
        }
        for (final RegisterUserStatus status : RegisterUserStatus.values()) {
            final RegisterUserResponse response = RegisterUserResponse.builder()
                    .message("m")
                    .status(status)
                    .userId(1L)
                    .build();
            final ResponseRegisterUserDTO actualResponse = this.mapper.asResponseRegisterUserDto(response);
            assertThat(actualResponse.getStatus().name()).isEqualTo(status.name());
        }

        //then
        assertThat(RegisterUserStatus.values().length).isGreaterThan(0);
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
