package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatAgentRequest {

    private UUID conversationId;

    private String message;
}
