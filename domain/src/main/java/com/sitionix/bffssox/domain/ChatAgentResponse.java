package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatAgentResponse {

    private UUID conversationId;

    private ChatAgentMessage reply;
}
