package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitChatExecutionResponse;
import java.util.UUID;

public interface SubmitAgentChatExecution {

    SubmitChatExecutionResponse execute(UUID agentId, ChatAgentRequest request, String idempotencyKey);
}
