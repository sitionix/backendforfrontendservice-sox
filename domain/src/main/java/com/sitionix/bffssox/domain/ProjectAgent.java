package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectAgent {

    private String id;

    private String name;

    private String description;

    private AgentStatus status;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String membershipId;

    private OffsetDateTime attachedAt;
}
