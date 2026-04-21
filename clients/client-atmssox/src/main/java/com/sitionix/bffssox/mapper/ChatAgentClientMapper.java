package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentClientMapper {

    ChatAgentRequestDTO asChatAgentRequestDto(ChatAgentRequest src);

    ChatAgentResponse asChatAgentResponse(ChatAgentResponseDTO src);
}
