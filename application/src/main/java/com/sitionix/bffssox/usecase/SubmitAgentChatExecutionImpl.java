package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentChatOperationsPort;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmitAgentChatExecutionImpl implements SubmitAgentChatExecution {

    private final AgentChatOperationsPort agentClient;

    @Override
    public SubmitChatExecutionResponse execute(final UUID agentId, final ChatAgentRequest request, final String idempotencyKey) {
        return this.agentClient.submitAgentChatExecution(agentId, request, idempotencyKey);
    }
}
