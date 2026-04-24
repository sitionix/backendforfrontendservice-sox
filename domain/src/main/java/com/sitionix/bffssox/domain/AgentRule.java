package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;

public record AgentRule(
        UUID id,
        String text,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
