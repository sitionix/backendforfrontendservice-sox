package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Agent {

    private String id;

    private String name;

    private String description;

    private AgentStatus status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
