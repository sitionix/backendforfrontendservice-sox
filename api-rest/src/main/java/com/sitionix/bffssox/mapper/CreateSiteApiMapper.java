package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.CreateSiteRequestDTO;
import com.app_afesox.bffssox.api_first.dto.CreateSiteResponseDTO;
import com.sitionix.bffssox.domain.CreateSiteRequest;
import com.sitionix.bffssox.domain.CreateSiteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateSiteApiMapper {

    CreateSiteRequest asCreateSiteRequest(CreateSiteRequestDTO src);

    CreateSiteResponseDTO asCreateSiteResponseDto(CreateSiteResponse src);
}
