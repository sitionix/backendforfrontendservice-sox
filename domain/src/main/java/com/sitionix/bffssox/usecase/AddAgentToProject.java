package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AddAgentToProjectRequest;
import com.sitionix.bffssox.domain.ProjectAgent;
import java.util.UUID;

public interface AddAgentToProject {

    ProjectAgent execute(UUID projectId, AddAgentToProjectRequest request);
}
