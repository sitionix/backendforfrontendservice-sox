package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectConversationDetails {

    private UUID id;

    private UUID projectId;

    private ProjectConversationProject project;

    private String type;

    private String title;

    private String status;

    private List<ProjectConversationParticipant> participants;

    private List<ChatAgentMessage> messages;

    private Boolean canSendMessages;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private OffsetDateTime lastMessageAt;
}
