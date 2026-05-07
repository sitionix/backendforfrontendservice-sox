package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ProjectAgentsResponse;
import java.util.UUID;

public interface GetProjectAgents {

    ProjectAgentsResponse execute(UUID projectId);
}
