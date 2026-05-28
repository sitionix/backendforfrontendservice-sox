package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectFlowEdge {

    private UUID sourceId;

    private UUID targetId;

    private String edgeType;
}
