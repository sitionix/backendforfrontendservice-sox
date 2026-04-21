package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentConversationDetails;
import java.util.UUID;

/**
 * Loads one direct conversation.
 */
public interface GetAgentConversation {

    /**
     * Returns one direct conversation with messages.
     *
     * @param conversationId unique conversation identifier.
     * @return direct conversation details.
     */
    AgentConversationDetails execute(UUID conversationId);
}
