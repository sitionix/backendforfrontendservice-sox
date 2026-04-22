package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentConversation {

    private UUID id;

    private String title;

    private String type;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastMessageAt;
}
