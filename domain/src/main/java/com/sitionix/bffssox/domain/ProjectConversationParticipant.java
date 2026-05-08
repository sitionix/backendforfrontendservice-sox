package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectConversationParticipant {

    private String type;

    private UUID userId;

    private UUID agentId;

    private String name;

    private String description;

    private String status;
}
