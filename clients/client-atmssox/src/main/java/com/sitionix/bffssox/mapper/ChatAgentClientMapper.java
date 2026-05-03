package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentExecutionDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.List;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR, uses = {
        ChatExecutionStatusClientMapper.class,
        ChatExecutionFailureClientMapper.class
})
public interface ChatAgentClientMapper {

    @Mapping(target = "clientRequestId", source = "clientRequestId")
    ChatAgentRequestDTO asChatAgentRequestDto(ChatAgentRequest src);

    @Mapping(target = "reply", source = "assistantMessage")
    ChatAgentResponse asChatAgentResponse(ChatAgentExecutionDTO src);

    @Mapping(target = "state", source = "status")
    @Mapping(target = "createdAt", source = "acceptedAt")
    @Mapping(target = "inputMessageId", source = "inputMessageId")
    SubmitChatExecutionResponse asSubmitChatExecutionResponse(SubmitChatExecutionResponseDTO src);

    @Mapping(target = "state", source = "status")
    @Mapping(target = "createdAt", source = "acceptedAt")
    @Mapping(target = "failure", source = "error")
    ChatExecution asChatExecution(ChatExecutionDTO src);

    AgentConversation asAgentConversation(AgentConversationDTO src);

    ChatAgentMessage asChatAgentMessage(AgentConversationMessageDTO src);

    List<AgentConversation> asAgentConversations(List<AgentConversationDTO> src);

    List<ChatAgentMessage> asChatAgentMessages(List<AgentConversationMessageDTO> src);

    @Mapping(target = "items", source = "items")
    AgentConversationsResponse asAgentConversationsResponse(AgentConversationsResponseDTO src);

    @Mapping(target = "type", source = "type")
    @Mapping(target = "messages", source = "messages")
    AgentConversationDetails asAgentConversationDetails(AgentConversationDetailsDTO src);

    default String map(final AgentConversationDetailsDTO.TypeEnum value) {
        return value == null ? null : value.getValue();
    }

    default String mapConversationType(final AgentConversationDTO.TypeEnum value) {
        return value == null ? null : value.getValue();
    }

    default String mapAuthorType(final AgentConversationMessageDTO.AuthorTypeEnum value) {
        return value == null ? null : value.getValue();
    }

}
