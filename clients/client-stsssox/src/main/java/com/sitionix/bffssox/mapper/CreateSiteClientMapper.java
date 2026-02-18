package com.sitionix.bffssox.mapper;

import com.app_afesox.stsssox.client.dto.CreateSiteRequestDTO;
import com.app_afesox.stsssox.client.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSiteClientMapper {

    CreateSiteRequestDTO asCreateSiteRequestDto(CreateSiteRequest src);

    CreateSiteResponse asCreateSiteResponse(CreateSiteResponseDTO src);
}
