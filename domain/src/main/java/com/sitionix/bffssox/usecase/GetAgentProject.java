package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProject;
import java.util.UUID;

public interface GetAgentProject {

    AgentProject execute(UUID projectId);
}
