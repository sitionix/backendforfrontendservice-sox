package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteAgentConversationImpl implements DeleteAgentConversation {

    private final AgentConversationClient agentClient;

    @Override
    public void execute(final UUID conversationId) {
        this.agentClient.deleteAgentConversation(conversationId);
    }
}
