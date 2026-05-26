package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitConversationExecutionResponse {

    private UUID conversationId;

    private UUID inputMessageId;

    private Boolean runtimeDispatched;

    private UUID executionId;

    private String executionStatus;
}
