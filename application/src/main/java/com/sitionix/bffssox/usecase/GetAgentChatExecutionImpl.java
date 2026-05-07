package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentChatClient;
import com.sitionix.bffssox.domain.ChatExecution;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentChatExecutionImpl implements GetAgentChatExecution {

    private final AgentChatClient agentClient;

    @Override
    public ChatExecution execute(final UUID agentId, final UUID executionId, final UUID conversationId) {
        return this.agentClient.getAgentChatExecution(agentId, executionId, conversationId);
    }
}
