package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.PatchAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import com.sitionix.bffssox.mapper.PatchAgentClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentAtmssoxClient implements AgentOperationsPort {

    private final AgentApi agentApi;
    private final CreateAgentClientMapper createAgentClientMapper;
    private final PatchAgentClientMapper patchAgentClientMapper;
    private final AgentClientMapper agentClientMapper;
    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    public Agent createAgent(final CreateAgentRequest request) {
        final CreateAgentRequestDTO requestDTO = this.createAgentClientMapper.asCreateAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.createAgent(requestDTO));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public AgentsResponse getAgents() {
        final AgentsResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(this.agentApi::getAgents);
        return this.agentClientMapper.asAgentsResponse(responseDTO);
    }

    public Agent getAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.getAgent(agentId));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public Agent activateAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.activateAgent(agentId));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public Agent archiveAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.archiveAgent(agentId));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public Agent restoreAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.restoreAgent(agentId));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public Agent deleteAgent(final UUID agentId) {
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.deleteAgent(agentId));
        return this.agentClientMapper.asAgent(responseDTO);
    }

    public Agent patchAgent(final UUID agentId, final PatchAgentRequest request) {
        final PatchAgentRequestDTO requestDTO = this.patchAgentClientMapper.asPatchAgentRequestDto(request);
        final AgentDTO responseDTO = this.atmssoxClientCallExecutor.execute(() -> this.agentApi.patchAgent(agentId, requestDTO));
        return this.agentClientMapper.asAgent(responseDTO);
    }
}
