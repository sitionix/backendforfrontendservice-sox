package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreateAgentClientMapper {

    CreateAgentRequestDTO asCreateAgentRequestDto(CreateAgentRequest src);
}
