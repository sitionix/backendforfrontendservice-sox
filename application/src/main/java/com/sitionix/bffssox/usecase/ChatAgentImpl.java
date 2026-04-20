package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentClient;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatAgentImpl implements ChatAgent {

    private final AgentClient agentClient;

    @Override
    public ChatAgentResponse execute(final UUID agentId, final ChatAgentRequest request) {
        return this.agentClient.chatAgent(agentId, request);
    }
}
