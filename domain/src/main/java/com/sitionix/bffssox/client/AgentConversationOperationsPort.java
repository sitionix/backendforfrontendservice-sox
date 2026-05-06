package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import java.util.UUID;

/**
 * Port for automation agent conversation operations.
 */
public interface AgentConversationOperationsPort {

    AgentConversationsResponse getAgentConversations(UUID agentId);

    AgentConversationDetails getAgentConversation(UUID conversationId);

    void deleteAgentConversation(UUID conversationId);
}
