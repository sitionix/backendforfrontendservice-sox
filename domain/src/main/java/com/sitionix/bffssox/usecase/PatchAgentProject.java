package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import java.util.UUID;

public interface PatchAgentProject {

    AgentProject execute(UUID projectId, PatchAgentProjectRequest request);
}
