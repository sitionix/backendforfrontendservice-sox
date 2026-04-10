package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentApi;
import com.app_afesox.atmssox.client.dto.AgentDTO;
import com.app_afesox.atmssox.client.dto.AgentsResponseDTO;
import com.app_afesox.atmssox.client.dto.CreateAgentRequestDTO;
import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import java.util.UUID;
import com.sitionix.bffssox.mapper.AgentClientMapper;
import com.sitionix.bffssox.mapper.CreateAgentClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentClientImpl implements com.sitionix.bffssox.client.AgentClient {

    private final AgentApi agentApi;

    private final CreateAgentClientMapper createAgentClientMapper;

    private final AgentClientMapper agentClientMapper;

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
}
