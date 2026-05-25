package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SubmitConversationExecutionImpl implements SubmitConversationExecution {

    private final AgentConversationClient agentConversationClient;

    @Override
    public SubmitConversationExecutionResponse execute(final UUID conversationId, final ChatAgentRequest request) {
        return this.agentConversationClient.submitConversationExecution(conversationId, request);
    }
}
