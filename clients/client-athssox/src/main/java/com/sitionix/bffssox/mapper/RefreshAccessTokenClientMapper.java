package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.RefreshAccessTokenRequestDTO;
import com.app_afesox.athssox.client.dto.RefreshAccessTokenResponseDTO;
import com.sitionix.bffssox.domain.RefreshAccessTokenRequest;
import com.sitionix.bffssox.domain.RefreshAccessTokenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RefreshAccessTokenClientMapper {

    RefreshAccessTokenRequestDTO asRefreshAccessTokenRequestDto(RefreshAccessTokenRequest src);

    RefreshAccessTokenResponse asRefreshAccessTokenResponse(RefreshAccessTokenResponseDTO src);
}
