package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.AgentConversationDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationMessageDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.api_first.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.app_afesox.atmssox.api_first.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.api_first.dto.ChatExecutionFailureDTO;
import com.app_afesox.atmssox.api_first.dto.ExecutionStatusDTO;
import com.app_afesox.atmssox.api_first.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.AgentConversation;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import com.sitionix.bffssox.domain.ChatAgentMessage;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.List;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatAgentClientMapper {

    ChatAgentRequestDTO asChatAgentRequestDto(ChatAgentRequest src);

    ChatAgentResponse asChatAgentResponse(ChatAgentResponseDTO src);

    SubmitChatExecutionResponse asSubmitChatExecutionResponse(SubmitChatExecutionResponseDTO src);

    ChatExecution asChatExecution(ChatExecutionDTO src);

    ChatExecutionFailure asChatExecutionFailure(ChatExecutionFailureDTO src);

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

    default String mapExecutionStatus(final ExecutionStatusDTO value) {
        return value == null ? null : value.getValue();
    }

    default String mapFailureClass(final ChatExecutionFailureDTO.FailureClassEnum value) {
        return value == null ? null : value.getValue();
    }
}
