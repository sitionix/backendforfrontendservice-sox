package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatExecution {

    private UUID executionId;

    private UUID conversationId;

    private UUID agentId;

    private String state;

    private OffsetDateTime createdAt;

    private OffsetDateTime startedAt;

    private OffsetDateTime completedAt;

    private ChatExecutionFailure failure;

    private ChatAgentMessage assistantMessage;
}
