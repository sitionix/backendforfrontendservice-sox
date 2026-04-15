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
        final String refreshToken = "refreshToken";
        final RefreshAccessTokenRequest expected = this.refreshAccessTokenRequest();
        final RefreshAccessTokenRequestDTO given = this.refreshAccessTokenRequestDTO();

        //when
        final RefreshAccessTokenRequest actual = this.mapper.asRefreshAccessTokenRequest(refreshToken, given);

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

    @Test
    void givenNullInputs_whenMap_thenReturnNull() {
        //given
        final RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO = null;
        final RefreshAccessTokenResponse refreshAccessTokenResponse = null;

        //when
        final RefreshAccessTokenRequest actualRequest =
                this.mapper.asRefreshAccessTokenRequest("refreshToken", refreshAccessTokenRequestDTO);
        final RefreshAccessTokenResponseDTO actualResponse = this.mapper.asRefreshAccessTokenResponseDTO(refreshAccessTokenResponse);

        //then
        assertThat(actualRequest).isEqualTo(RefreshAccessTokenRequest.builder()
                .refreshToken("refreshToken")
                .sessionSourceId(null)
                .build());
        assertThat(actualResponse).isNull();
    }

    @Test
    void givenTokenType_whenAsTokenTypeEnum_thenReturnEnum() {
        //given
        final String given = "Bearer";

        //when
        final RefreshAccessTokenResponseDTO.TokenTypeEnum actual = this.mapper.asTokenTypeEnum(given);

        //then
        assertThat(actual).isEqualTo(RefreshAccessTokenResponseDTO.TokenTypeEnum.BEARER);
    }

    @Test
    void givenNullTokenType_whenAsTokenTypeEnum_thenReturnNull() {
        //given
        final String given = null;

        //when
        final RefreshAccessTokenResponseDTO.TokenTypeEnum actual = this.mapper.asTokenTypeEnum(given);

        //then
        assertThat(actual).isNull();
    }

    @Test
    void givenTokenTypeEnum_whenAsTokenTypeValue_thenReturnValue() {
        //given
        final RefreshAccessTokenResponseDTO.TokenTypeEnum given = RefreshAccessTokenResponseDTO.TokenTypeEnum.BEARER;

        //when
        final String actual = this.mapper.asTokenTypeValue(given);

        //then
        assertThat(actual).isEqualTo("Bearer");
    }

    @Test
    void givenNullTokenTypeEnum_whenAsTokenTypeValue_thenReturnNull() {
        //given
        final RefreshAccessTokenResponseDTO.TokenTypeEnum given = null;

        //when
        final String actual = this.mapper.asTokenTypeValue(given);

        //then
        assertThat(actual).isNull();
    }

    private RefreshAccessTokenRequestDTO refreshAccessTokenRequestDTO() {
        return RefreshAccessTokenRequestDTO.builder()
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
