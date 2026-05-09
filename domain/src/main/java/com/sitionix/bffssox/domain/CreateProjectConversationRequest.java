package com.sitionix.bffssox.domain;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateProjectConversationRequest {

    private List<UUID> agentIds;
}
