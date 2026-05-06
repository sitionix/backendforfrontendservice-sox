package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.CreateAgentProjectRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAgentProjectClientMapper {

    CreateAgentProjectRequestDTO asCreateAgentProjectRequestDto(CreateAgentProjectRequest src);
}
