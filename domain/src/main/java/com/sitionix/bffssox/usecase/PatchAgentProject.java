package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProject;
import com.sitionix.bffssox.domain.PatchAgentProjectRequest;
import java.util.UUID;

/**
 * Updates editable fields of an agent project owned by the current user.
 */
public interface PatchAgentProject {

    /**
     * Applies patch request to the target agent project.
     *
     * @param projectId target project identifier
     * @param request patch payload with allowed fields
     * @return updated agent project
     */
    AgentProject execute(UUID projectId, PatchAgentProjectRequest request);
}
