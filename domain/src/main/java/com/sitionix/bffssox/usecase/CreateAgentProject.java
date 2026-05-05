package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.CreateAgentProjectRequest;

public interface CreateAgentProject {

    AgentProject execute(CreateAgentProjectRequest request);
}
