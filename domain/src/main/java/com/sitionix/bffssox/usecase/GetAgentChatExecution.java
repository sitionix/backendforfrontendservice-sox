package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ChatExecution;
import java.util.UUID;

public interface GetAgentChatExecution {

    ChatExecution execute(UUID agentId, UUID executionId, UUID conversationId);
}
