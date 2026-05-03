package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubmitChatExecutionResponse {

    private UUID executionId;

    private UUID conversationId;

    private UUID inputMessageId;

    private String state;

    private OffsetDateTime createdAt;

    private String idempotencyKey;

    private Boolean idempotencyReplayed;
}
