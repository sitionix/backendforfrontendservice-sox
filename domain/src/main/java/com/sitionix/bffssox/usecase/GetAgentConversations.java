package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentConversationsResponse;
import java.util.UUID;

/**
 * Loads direct conversations for one automation agent.
 */
public interface GetAgentConversations {

    /**
     * Returns conversations for one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return direct conversations response.
     */
    AgentConversationsResponse execute(UUID agentId);
}
