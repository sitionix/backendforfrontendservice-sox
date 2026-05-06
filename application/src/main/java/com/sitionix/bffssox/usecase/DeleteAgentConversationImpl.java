package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationOperationsPort;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAgentConversationImpl implements DeleteAgentConversation {

    private final AgentConversationOperationsPort agentClient;

    @Override
    public void execute(final UUID conversationId) {
        this.agentClient.deleteAgentConversation(conversationId);
    }
}
