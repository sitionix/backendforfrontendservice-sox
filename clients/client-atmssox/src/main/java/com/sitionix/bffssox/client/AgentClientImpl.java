package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentConversationDetailsDTO;
import com.app_afesox.atmssox.client.dto.AgentConversationsResponseDTO;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentClientImpl implements com.sitionix.bffssox.client.AgentClient {

    private final AgentApi agentApi;

    private final CreateAgentClientMapper createAgentClientMapper;

    private final AgentClientMapper agentClientMapper;

    private final PatchAgentClientMapper patchAgentClientMapper;

    private final ChatAgentClientMapper chatAgentClientMapper;

    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    @Override
    public Agent createAgent(final CreateAgentRequest request) {
        final CreateAgentRequestDTO requestDTO = this.createAgentClientMapper.asCreateAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.createAgent(requestDTO)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public AgentsResponse getAgents() {
        final AgentsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(this.agentApi::getAgents);
        return this.agentClientMapper.asAgentsResponse(responseDTO);
    }

    @Override
    public Agent getAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public AgentConversationsResponse getAgentConversations(final UUID agentId) {
        final AgentConversationsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgentConversations(agentId)
        );
        return this.chatAgentClientMapper.asAgentConversationsResponse(responseDTO);
    }

    @Override
    public AgentConversationDetails getAgentConversation(final UUID conversationId) {
        final AgentConversationDetailsDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.getAgentConversation(conversationId)
        );
        return this.chatAgentClientMapper.asAgentConversationDetails(responseDTO);
    }

    @Override
    public Agent activateAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.activateAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent archiveAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.archiveAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent restoreAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.restoreAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public Agent deleteAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.deleteAgent(agentId)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }

    @Override
    public ChatAgentResponse chatAgent(final UUID agentId, final ChatAgentRequest request) {
        final ChatAgentRequestDTO requestDTO = this.chatAgentClientMapper.asChatAgentRequestDto(request);
        final ChatAgentResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.chatAgent(agentId, requestDTO)
        );
        return this.chatAgentClientMapper.asChatAgentResponse(responseDTO);
    }

    @Override
    public Agent patchAgent(final UUID agentId, final PatchAgentRequest request) {
        final PatchAgentRequestDTO requestDTO = this.patchAgentClientMapper.asPatchAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentApi.patchAgent(agentId, requestDTO)
        );
        return this.agentClientMapper.asAgent(responseDTO);
    }
}
