package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProject;
import java.util.UUID;

/**
 * Use case contract for retrieving a single agent project available to the current user.
 */
public interface GetAgentProject {

    /**
     * Retrieves a visible agent project by its identifier for the current user context.
     *
     * @param projectId project identifier
     * @return found agent project
     */
    AgentProject execute(UUID projectId);
}
