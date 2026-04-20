package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.ChatAgentResponse;
import java.util.UUID;

/**
 * Executes one chat request for one automation agent.
 */
public interface ChatAgent {

    /**
     * Executes one request-response chat interaction.
     *
     * @param agentId unique agent identifier.
     * @param request chat request payload.
     * @return normalized assistant reply payload.
     */
    ChatAgentResponse execute(UUID agentId, ChatAgentRequest request);
}
