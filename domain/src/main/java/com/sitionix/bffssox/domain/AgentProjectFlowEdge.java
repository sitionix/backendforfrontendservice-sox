package com.sitionix.bffssox.domain;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectFlowEdge {

    private UUID id;

    private UUID sourceNodeId;

    private UUID targetNodeId;

    private String edgeType;

    private Map<String, Object> config;
}
