package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RefreshAccessTokenApiMapperTest {

    private RefreshAccessTokenApiMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new RefreshAccessTokenApiMapperImpl();
    }

    @Test
    void givenRefreshAccessTokenRequestDto_whenAsRefreshAccessTokenRequest_thenReturnRequest() {
        //given
        final RefreshAccessTokenRequest expected = this.refreshAccessTokenRequest();
        final RefreshAccessTokenRequestDTO given = this.refreshAccessTokenRequestDTO();

        //when
        final RefreshAccessTokenRequest actual = this.mapper.asRefreshAccessTokenRequest(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenRefreshAccessTokenResponse_whenAsRefreshAccessTokenResponseDto_thenReturnResponseDto() {
        //given
        final RefreshAccessTokenResponseDTO expected = this.refreshAccessTokenResponseDTO();
        final RefreshAccessTokenResponse given = this.refreshAccessTokenResponse();

        //when
        final RefreshAccessTokenResponseDTO actual = this.mapper.asRefreshAccessTokenResponseDTO(given);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO() {
        return RefreshAccessTokenRequestDTO.builder()
                .refreshToken("refreshToken")
                .sessionSourceId("sessionSourceId")
                .build();
    }

    private RefreshAccessTokenRequest refreshAccessTokenRequest() {
        return RefreshAccessTokenRequest.builder()
                .refreshToken("refreshToken")
                .sessionSourceId("sessionSourceId")
                .build();
    }

    private RefreshAccessTokenResponseDTO refreshAccessTokenResponseDTO() {
        return RefreshAccessTokenResponseDTO.builder()
                .refreshToken("refreshToken")
                .expiresIn(3600L)
                .tokenType(RefreshAccessTokenResponseDTO.TokenTypeEnum.BEARER)
                .accessToken("accessToken")
                .build();
    }

    private RefreshAccessTokenResponse refreshAccessTokenResponse() {
        return RefreshAccessTokenResponse.builder()
                .refreshToken("refreshToken")
                .expiresIn(3600L)
                .tokenType("Bearer")
                .accessToken("accessToken")
                .build();
    }
}
