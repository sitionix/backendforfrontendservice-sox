package com.sitionix.bffssox.client;

import com.app_afesox.atmssox.client.api.AgentChatApi;
import com.app_afesox.atmssox.client.dto.ChatExecutionDTO;
import com.app_afesox.atmssox.client.dto.ChatAgentRequestDTO;
import com.app_afesox.atmssox.client.dto.SubmitChatExecutionResponseDTO;
import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import com.sitionix.bffssox.mapper.ChatAgentClientMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentChatAtmssoxClient implements AgentChatClient {

    private final AgentChatApi agentChatApi;
    private final ChatAgentClientMapper chatAgentClientMapper;
    private final AtmssoxClientCallExecutor atmssoxClientCallExecutor;

    public SubmitChatExecutionResponse submitAgentChatExecution(final UUID agentId,
                                                                final ChatAgentRequest request,
                                                                final String idempotencyKey) {
        final ChatAgentRequestDTO requestDTO = this.chatAgentClientMapper.asChatAgentRequestDto(request);
        final SubmitChatExecutionResponseDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentChatApi.submitAgentChatExecutionByExecutionsPath(agentId, requestDTO, idempotencyKey)
        );
        return this.chatAgentClientMapper.asSubmitChatExecutionResponse(responseDTO);
    }

    @Override
    public ChatExecution getAgentChatExecution(final UUID agentId, final UUID executionId, final UUID conversationId) {
        final ChatExecutionDTO responseDTO = this.atmssoxClientCallExecutor.execute(
                () -> this.agentChatApi.getAgentChatExecution(agentId, executionId, conversationId)
        );
        return this.chatAgentClientMapper.asChatExecution(responseDTO);
    }
}
