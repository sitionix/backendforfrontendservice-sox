package com.sitionix.bffssox.domain;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatAgentMessage {

    private UUID id;

    private String authorType;

    private String authorId;

    private String content;

    private OffsetDateTime createdAt;
}
