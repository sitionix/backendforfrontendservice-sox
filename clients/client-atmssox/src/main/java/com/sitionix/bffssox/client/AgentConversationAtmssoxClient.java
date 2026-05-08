package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentConversationApi;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateProjectConversationRequestDTO;
import com.app_afesox.atmssox.client.dto.ProjectConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.ProjectConversationsResponseDTO;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentConversationAtmssoxClient implements AgentConversationClient {

    private final AgentConversationApi agentConversationApi;
    private final ChatAgentClientMapper chatAgentClientMapper;
    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    public AgentConversationsResponse getAgentConversations(final UUID agentId) {
        final AgentConversationsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentConversationApi.getAgentConversations(agentId)
        );
        return this.chatAgentClientMapper.asAgentConversationsResponse(responseDTO);
    }

    public AgentConversationDetails getAgentConversation(final UUID conversationId) {
        final AgentConversationDetailsDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentConversationApi.getAgentConversation(conversationId)
        );
        return this.chatAgentClientMapper.asAgentConversationDetails(responseDTO);
    }

    public void deleteAgentConversation(final UUID conversationId) {
        this.atmssoxClientCallExecutor.execute(() -> {
            this.agentConversationApi.deleteAgentConversation(conversationId);
            return null;
        });
    }

    @Override
    public ProjectConversationDetails createProjectConversation(final UUID projectId, final CreateProjectConversationRequest request) {
        final CreateProjectConversationRequestDTO requestDTO = this.chatAgentClientMapper.asCreateProjectConversationRequestDto(request);
        final ProjectConversationDetailsDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentConversationApi.createProjectConversation(projectId, requestDTO)
        );
        return this.chatAgentClientMapper.asProjectConversationDetails(responseDTO);
    }

    @Override
    public ProjectConversationsResponse listProjectConversations(final UUID projectId) {
        final ProjectConversationsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentConversationApi.listProjectConversations(projectId)
        );
        return this.chatAgentClientMapper.asProjectConversationsResponse(responseDTO);
    }

    @Override
    public ProjectConversationDetails getProjectConversation(final UUID projectId, final UUID conversationId) {
        final ProjectConversationDetailsDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentConversationApi.getProjectConversation(projectId, conversationId)
        );
        return this.chatAgentClientMapper.asProjectConversationDetails(responseDTO);
    }
}
