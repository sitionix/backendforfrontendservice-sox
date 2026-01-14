package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RefreshAccessTokenClientMapperTest {

    private RefreshAccessTokenClientMapper mapper;

    @BeforeEach
    void setUp() {
        this.mapper = new RefreshAccessTokenClientMapperImpl();
    }

    @Test
    void givenRefreshAccessTokenRequest_whenAsRefreshAccessTokenRequestDto_thenReturnRequestDto() {
        //given
        final RefreshAccessTokenRequest request = this.refreshAccessTokenRequest();
        final RefreshAccessTokenRequestDTO expected = this.refreshAccessTokenRequestDTO();

        //when
        final RefreshAccessTokenRequestDTO actual = this.mapper.asRefreshAccessTokenRequestDto(request);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void givenRefreshAccessTokenResponseDto_whenAsRefreshAccessTokenResponse_thenReturnResponse() {
        //given
        final RefreshAccessTokenResponseDTO responseDTO = this.refreshAccessTokenResponseDTO();
        final RefreshAccessTokenResponse expected = this.refreshAccessTokenResponse();

        //when
        final RefreshAccessTokenResponse actual = this.mapper.asRefreshAccessTokenResponse(responseDTO);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    private RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO() {
        return new RefreshAccessTokenRequestDTO()
                .refreshToken("refreshToken")
                .sessionSourceId("sessionSourceId");
    }

    private RefreshAccessTokenRequest refreshAccessTokenRequest() {
        return RefreshAccessTokenRequest.builder()
                .refreshToken("refreshToken")
                .sessionSourceId("sessionSourceId")
                .build();
    }

    private RefreshAccessTokenResponseDTO refreshAccessTokenResponseDTO() {
        return new RefreshAccessTokenResponseDTO()
                .refreshToken("refreshToken")
                .expiresIn(3600L)
                .tokenType("Bearer")
                .accessToken("accessToken");
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
