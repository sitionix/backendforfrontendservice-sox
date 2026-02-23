package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.app_afesox.bffssox.api_first.dto.SessionUserDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.SessionResponse;
import com.sitionix.bffssox.domain.SessionUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.OffsetDateTime;
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
    void givenSessionResponse_whenAsLoginResponseDto_thenReturnLoginResponseDto() {
        //given
        final LoginResponseDTO expected = this.loginResponseDTO();
        final SessionResponse given = this.sessionResponse();

        //when
        final LoginResponseDTO actual = this.mapper.asLoginResponseDTO(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private LoginRequestDTO loginRequestDTO(final UUID uuid) {
        return LoginRequestDTO.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .build();
    }

    private LoginRequest loginRequest(final UUID uuid) {
        return LoginRequest.builder()
                .email("email")
                .password("password")
                .siteId(uuid)
                .build();
    }

    private LoginResponseDTO loginResponseDTO() {
        return LoginResponseDTO.builder()
                .authenticated(Boolean.TRUE)
                .user(SessionUserDTO.builder()
                        .id("123")
                        .email("email@example.com")
                        .role("SUPER_ADMIN")
                        .siteId(UUID.fromString("261f6b83-f95f-4ab2-be1b-70f5c2ee7f54"))
                        .build())
                .expiresAt(OffsetDateTime.parse("2030-01-01T00:00:00Z"))
                .idleTimeoutSeconds(86_400L)
                .build();
    }

    private SessionResponse sessionResponse() {
        return SessionResponse.builder()
                .authenticated(Boolean.TRUE)
                .user(SessionUser.builder()
                        .id("123")
                        .email("email@example.com")
                        .role("SUPER_ADMIN")
                        .siteId(UUID.fromString("261f6b83-f95f-4ab2-be1b-70f5c2ee7f54"))
                        .build())
                .expiresAt(Instant.parse("2030-01-01T00:00:00Z"))
                .idleTimeoutSeconds(86_400L)
                .build();
    }
}
