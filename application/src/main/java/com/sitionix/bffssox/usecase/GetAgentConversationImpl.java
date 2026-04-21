package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentConversationDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentConversationImpl implements GetAgentConversation {

    private final AgentClient agentClient;

    @Override
    public AgentConversationDetails execute(final UUID agentId, final UUID conversationId) {
        return this.agentClient.getAgentConversation(agentId, conversationId);
    }
}
