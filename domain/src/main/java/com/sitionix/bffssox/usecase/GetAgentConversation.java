package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentConversationDetails;
import java.util.UUID;

/**
 * Loads one direct conversation for one automation agent.
 */
public interface GetAgentConversation {

    /**
     * Returns one direct conversation with messages.
     *
     * @param agentId unique agent identifier.
     * @param conversationId unique conversation identifier.
     * @return direct conversation details.
     */
    AgentConversationDetails execute(UUID agentId, UUID conversationId);
}
