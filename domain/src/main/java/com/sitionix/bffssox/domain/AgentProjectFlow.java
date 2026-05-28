package com.sitionix.bffssox.domain;

import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectFlow {

    private UUID flowId;

    private List<AgentProjectFlowNode> nodes;

    private List<AgentProjectFlowEdge> edges;
}
