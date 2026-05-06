package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentConversationApi;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.DeleteAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversations;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgentConversationController implements AgentConversationApi {

    private final ChatAgentApiMapper chatAgentApiMapper;
    private final GetAgentConversations getAgentConversations;
    private final GetAgentConversation getAgentConversation;
    private final DeleteAgentConversation deleteAgentConversation;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentConversationsResponseDTO> getAgentConversations(final UUID agentId) {
        final AgentConversationsResponse response = this.getAgentConversations.execute(agentId);
        return ResponseEntity.ok(this.chatAgentApiMapper.asAgentConversationsResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AgentConversationDetailsDTO> getAgentConversation(final UUID conversationId) {
        final AgentConversationDetails response = this.getAgentConversation.execute(conversationId);
        return ResponseEntity.ok(this.chatAgentApiMapper.asAgentConversationDetailsDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteAgentConversation(final UUID conversationId) {
        this.deleteAgentConversation.execute(conversationId);
        return ResponseEntity.noContent().build();
    }
}
