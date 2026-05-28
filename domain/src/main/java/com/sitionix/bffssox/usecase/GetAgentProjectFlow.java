package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProjectFlowResponse;
import java.util.UUID;

public interface GetAgentProjectFlow {

    AgentProjectFlowResponse execute(UUID projectId);
}
