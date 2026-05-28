package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProjectFlow;
import java.util.UUID;

public interface GetAgentProjectFlow {

    AgentProjectFlow execute(UUID projectId);
}
