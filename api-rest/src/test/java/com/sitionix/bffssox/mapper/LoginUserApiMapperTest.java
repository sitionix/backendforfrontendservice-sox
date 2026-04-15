package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LoginUserApiMapperTest {

    private LoginUserApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new LoginUserApiMapperImpl();
    }

    @Test
    void givenLoginRequestDto_whenAsLoginRequest_thenReturnLoginRequest() {
        //given
        final UUID uuid = UUID.randomUUID();
        final LoginRequest expected = this.loginRequest(uuid);
        final LoginRequestDTO given = this.loginRequestDTO(uuid);

        //when
        final LoginRequest actual = this.mapper.asLoginRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenLoginResponse_whenAsLoginResponseDto_thenReturnLoginResponseDto() {
        //given
        final LoginResponseDTO expected = this.loginResponseDTO();
        final LoginResponse given = this.loginResponse();

        //when
        final LoginResponseDTO actual = this.mapper.asLoginResponseDTO(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final LoginRequestDTO loginRequestDTO = null;
        final LoginResponse loginResponse = null;

        //when
        final LoginRequest actualRequest = this.mapper.asLoginRequest(loginRequestDTO);
        final LoginResponseDTO actualResponse = this.mapper.asLoginResponseDTO(loginResponse);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    private LoginRequestDTO loginRequestDTO(final UUID uuid) {
        return LoginRequestDTO.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .userAgent("userAgent")
                .sessionSourceId("sessionSourceId")
                .build();
    }

    private LoginRequest loginRequest(final UUID uuid) {
        return LoginRequest.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .userAgent("userAgent")
                .sessionSourceId("sessionSourceId")
                .build();
    }

    private LoginResponseDTO loginResponseDTO() {
        return LoginResponseDTO.builder()
                .expiresIn(3600L)
                .tokenType("tokenType")
                .accessToken("accessToken")
                .build();
    }

    private LoginResponse loginResponse() {
        return LoginResponse.builder()
                .refreshToken("refreshToken")
                .expiresIn(3600L)
                .tokenType("tokenType")
                .accessToken("accessToken")
                .build();
    }
}
