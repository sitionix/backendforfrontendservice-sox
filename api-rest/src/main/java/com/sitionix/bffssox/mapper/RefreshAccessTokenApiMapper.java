package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.bffssox.api_first.dto.RefreshAccessTokenResponseDTO;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshAccessTokenApiMapper {

    RefreshAccessTokenRequest asRefreshAccessTokenRequest(final RefreshAccessTokenRequestDTO src);

    RefreshAccessTokenResponseDTO asRefreshAccessTokenResponseDTO(final RefreshAccessTokenResponse src);

    default RefreshAccessTokenResponseDTO.TokenTypeEnum asTokenTypeEnum(final String tokenType) {
        return tokenType == null ? null : RefreshAccessTokenResponseDTO.TokenTypeEnum.fromValue(tokenType);
    }

    default String asTokenTypeValue(final RefreshAccessTokenResponseDTO.TokenTypeEnum tokenType) {
        return tokenType == null ? null : tokenType.getValue();
    }
}
