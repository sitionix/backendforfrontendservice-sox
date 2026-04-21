package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentClientMapper {

    ChatAgentRequestDTO asChatAgentRequestDto(ChatAgentRequest src);

    ChatAgentResponse asChatAgentResponse(ChatAgentResponseDTO src);

    AgentConversation asAgentConversation(AgentConversationDTO src);

    ChatAgentMessage asChatAgentMessage(AgentConversationMessageDTO src);

    List<AgentConversation> asAgentConversations(List<AgentConversationDTO> src);

    List<ChatAgentMessage> asChatAgentMessages(List<AgentConversationMessageDTO> src);

    default AgentConversationsResponse asAgentConversationsResponse(final AgentConversationsResponseDTO src) {
        return AgentConversationsResponse.builder()
                .items(this.asAgentConversations(src.getItems()))
                .build();
    }

    default AgentConversationDetails asAgentConversationDetails(final AgentConversationDetailsDTO src) {
        return AgentConversationDetails.builder()
                .id(src.getId())
                .title(src.getTitle())
                .type(src.getType().getValue())
                .createdAt(src.getCreatedAt())
                .updatedAt(src.getUpdatedAt())
                .lastMessageAt(src.getLastMessageAt())
                .messages(this.asChatAgentMessages(src.getMessages()))
                .build();
    }
}
