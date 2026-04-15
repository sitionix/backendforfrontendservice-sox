package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
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
class RegisterUserClientMapperTest {

    private RegisterUserClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new RegisterUserClientMapperImpl();
    }

    @Test
    void givenRegisterUserRequest_whenAsRegisterUserDto_thenReturnRegisterUserDto() {
        //given
        final UUID uuid = UUID.randomUUID();
        final RegisterUserRequest registerUserRequest = this.registerUserRequest(uuid);
        final RegisterUserDTO expected = this.registerUserDTO(uuid);

        //when
        final RegisterUserDTO actual = this.mapper.asRegisterUserDto(registerUserRequest);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenResponseRegisterUserDto_whenAsRegisterUserResponse_thenReturnRegisterUserResponse() {
        //given
        final ResponseRegisterUserDTO responseRegisterUserDTO = this.responseRegisterUserDTO();
        final RegisterUserResponse expected = this.registerUserResponse();

        //when
        final RegisterUserResponse actual = this.mapper.asRegisterUserResponse(responseRegisterUserDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final RegisterUserRequest registerUserRequest = null;
        final ResponseRegisterUserDTO responseRegisterUserDTO = null;

        //when
        final RegisterUserDTO actualRequest = this.mapper.asRegisterUserDto(registerUserRequest);
        final RegisterUserResponse actualResponse = this.mapper.asRegisterUserResponse(responseRegisterUserDTO);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    @Test
    void givenAllRoleAndStatusVariants_whenMap_thenReturnMappedVariants() {
        //given
        final RegisterUserRequest registerUserRequest = RegisterUserRequest.builder()
                .email("e")
                .password("p")
                .siteId(UUID.fromString("00000000-0000-0000-0000-000000000001"))
                .role(RegisterUserRole.SUPER_ADMIN)
                .build();
        final ResponseRegisterUserDTO responseRegisterUserDTO = new ResponseRegisterUserDTO()
                .message("m")
                .status(ResponseRegisterUserDTO.StatusEnum.BANNED)
                .userId(10L);

        //when
        final RegisterUserDTO actualRequest = this.mapper.asRegisterUserDto(registerUserRequest);
        final RegisterUserResponse actualResponse = this.mapper.asRegisterUserResponse(responseRegisterUserDTO);

        //then
        assertThat(actualRequest.getRole()).isEqualTo(RegisterUserDTO.RoleEnum.SUPER_ADMIN);
        assertThat(actualResponse.getStatus()).isEqualTo(RegisterUserStatus.BANNED);
    }

    @Test
    void givenAllEnums_whenMap_thenReturnMappedEnums() {
        //given
        final UUID siteId = UUID.fromString("00000000-0000-0000-0000-000000000006");

        //when
        for (final RegisterUserRole role : RegisterUserRole.values()) {
            final RegisterUserRequest request = RegisterUserRequest.builder()
                    .email("e")
                    .password("p")
                    .siteId(siteId)
                    .role(role)
                    .build();
            final RegisterUserDTO actualRequest = this.mapper.asRegisterUserDto(request);
            assertThat(actualRequest.getRole().name()).isEqualTo(role.name());
        }
        for (final ResponseRegisterUserDTO.StatusEnum statusEnum : ResponseRegisterUserDTO.StatusEnum.values()) {
            final ResponseRegisterUserDTO responseDTO = new ResponseRegisterUserDTO()
                    .message("m")
                    .status(statusEnum)
                    .userId(10L);
            final RegisterUserResponse actualResponse = this.mapper.asRegisterUserResponse(responseDTO);
            assertThat(actualResponse.getStatus().name()).isEqualTo(statusEnum.name());
        }

        //then
        assertThat(ResponseRegisterUserDTO.StatusEnum.values().length).isGreaterThan(0);
    }

    private RegisterUserDTO registerUserDTO(final UUID uuid) {
        return new RegisterUserDTO()
                .email("email")
                .password("password")
                .siteId(uuid)
                .role(RegisterUserDTO.RoleEnum.SITE_ADMIN);
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
        return new ResponseRegisterUserDTO()
                .message("message")
                .status(ResponseRegisterUserDTO.StatusEnum.ACTIVE)
                .userId(10L);
    }

    private RegisterUserResponse registerUserResponse() {
        return RegisterUserResponse.builder()
                .message("message")
                .status(RegisterUserStatus.ACTIVE)
                .userId(10L)
                .build();
    }
}
