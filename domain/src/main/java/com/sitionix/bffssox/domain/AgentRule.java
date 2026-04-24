package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AgentRule(
        UUID id,
        UUID agentId,
        String title,
        String content,
        String status,
        String authorType,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
