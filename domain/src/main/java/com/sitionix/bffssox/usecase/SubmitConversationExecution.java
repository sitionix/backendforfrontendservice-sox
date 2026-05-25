package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ChatAgentRequest;
import com.sitionix.bffssox.domain.SubmitConversationExecutionResponse;
import java.util.UUID;

public interface SubmitConversationExecution {

    SubmitConversationExecutionResponse execute(UUID conversationId, ChatAgentRequest request);
}
