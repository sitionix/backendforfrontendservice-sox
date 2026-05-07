package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.ChatExecution;
import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.UUID;

/**
 * Port for automation agent chat execution operations.
 */
public interface AgentChatClient {

    SubmitChatExecutionResponse submitAgentChatExecution(UUID agentId, ChatAgentRequest request, String idempotencyKey);

    ChatExecution getAgentChatExecution(UUID agentId, UUID executionId, UUID conversationId);
}
