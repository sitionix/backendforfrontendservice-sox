package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class LoginUserClientMapperTest {

    private LoginUserClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new LoginUserClientMapperImpl();
    }

    @Test
    void givenLoginRequest_whenAsLoginRequestDto_thenReturnLoginRequestDto() {
        //given
        final UUID uuid = UUID.randomUUID();
        final LoginRequest loginRequest = this.loginRequest(uuid);
        final LoginRequestDTO expected = this.loginRequestDTO(uuid);

        //when
        final LoginRequestDTO actual = this.mapper.asLoginRequestDto(loginRequest);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenLoginResponseDTO_whenAsLoginResponse_thenReturnLoginResponse() {
        //given
        final LoginResponseDTO loginResponseDTO = this.loginResponseDTO();
        final LoginResponse expected = this.loginResponse();

        //when
        final LoginResponse actual = this.mapper.asLoginResponse(loginResponseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final LoginRequest loginRequest = null;
        final LoginResponseDTO loginResponseDTO = null;

        //when
        final LoginRequestDTO actualRequest = this.mapper.asLoginRequestDto(loginRequest);
        final LoginResponse actualResponse = this.mapper.asLoginResponse(loginResponseDTO);

        //then
        assertThat(actualRequest).isNull();
        assertThat(actualResponse).isNull();
    }

    private LoginRequestDTO loginRequestDTO(final UUID uuid) {
        return new LoginRequestDTO()
                .email("email")
                .password("password")
                .siteId(uuid)
                .userAgent("userAgent")
                .sessionSourceId("sessionSourceId");
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
        return new LoginResponseDTO()
                .refreshToken("refreshToken")
                .expiresIn(3600L)
                .tokenType("tokenType")
                .accessToken("accessToken");
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
