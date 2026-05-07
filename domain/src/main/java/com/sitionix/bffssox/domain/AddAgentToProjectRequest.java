package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddAgentToProjectRequest {

    private String agentId;
}
