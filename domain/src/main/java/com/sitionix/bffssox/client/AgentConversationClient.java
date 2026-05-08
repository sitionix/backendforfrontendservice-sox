package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.AgentConversationDetails;
import com.sitionix.bffssox.domain.AgentConversationsResponse;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import java.util.UUID;

/**
 * Port for automation agent conversation operations.
 */
public interface AgentConversationClient {

    AgentConversationsResponse getAgentConversations(UUID agentId);

    AgentConversationDetails getAgentConversation(UUID conversationId);

    void deleteAgentConversation(UUID conversationId);

    ProjectConversationDetails createProjectConversation(UUID projectId, CreateProjectConversationRequest request);

    ProjectConversationsResponse listProjectConversations(UUID projectId);

    ProjectConversationDetails getProjectConversation(UUID projectId, UUID conversationId);
}
