package com.sitionix.bffssox.controller;

import com.app_afesox.bffssox.api_first.api.AgentConversationApi;
import com.app_afesox.bffssox.api_first.dto.AgentConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.AgentConversationsResponseDTO;
import com.app_afesox.bffssox.api_first.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationDetailsDTO;
import com.app_afesox.bffssox.api_first.dto.ProjectConversationsResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import com.sitionix.bffssox.mapper.ChatAgentApiMapper;
import com.sitionix.bffssox.usecase.CreateProjectConversation;
import com.sitionix.bffssox.usecase.DeleteAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversation;
import com.sitionix.bffssox.usecase.GetAgentConversations;
import com.sitionix.bffssox.usecase.GetProjectConversation;
import com.sitionix.bffssox.usecase.ListProjectConversations;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final CreateProjectConversation createProjectConversation;
    private final ListProjectConversations listProjectConversations;
    private final GetProjectConversation getProjectConversation;

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

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectConversationDetailsDTO> createProjectConversation(final UUID projectId,
                                                                                   @Valid final CreateProjectConversationRequestDTO createProjectConversationRequestDTO) {
        final CreateProjectConversationRequest request = this.chatAgentApiMapper.asCreateProjectConversationRequest(createProjectConversationRequestDTO);
        final ProjectConversationDetails response = this.createProjectConversation.execute(projectId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.chatAgentApiMapper.asProjectConversationDetailsDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectConversationsResponseDTO> listProjectConversations(final UUID projectId) {
        final ProjectConversationsResponse response = this.listProjectConversations.execute(projectId);
        return ResponseEntity.ok(this.chatAgentApiMapper.asProjectConversationsResponseDto(response));
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ProjectConversationDetailsDTO> getProjectConversation(final UUID projectId, final UUID conversationId) {
        final ProjectConversationDetails response = this.getProjectConversation.execute(projectId, conversationId);
        return ResponseEntity.ok(this.chatAgentApiMapper.asProjectConversationDetailsDto(response));
    }
}
