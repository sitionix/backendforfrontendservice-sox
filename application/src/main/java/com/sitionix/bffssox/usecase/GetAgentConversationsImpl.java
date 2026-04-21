package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentConversationsImpl implements GetAgentConversations {

    private final AgentClient agentClient;

    @Override
    public AgentConversationsResponse execute(final UUID agentId) {
        return this.agentClient.getAgentConversations(agentId);
    }
}
