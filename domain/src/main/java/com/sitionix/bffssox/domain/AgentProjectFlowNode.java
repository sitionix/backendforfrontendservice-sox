package com.sitionix.bffssox.domain;

import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectFlowNode {

    private UUID id;

    private String nodeType;

    private UUID referenceId;

    private AgentProjectFlowNodePosition position;

    private String designStatus;

    private Map<String, Object> config;
}
