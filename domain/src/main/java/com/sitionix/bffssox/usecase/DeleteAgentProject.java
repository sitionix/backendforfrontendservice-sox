package com.sitionix.bffssox.usecase;

import java.util.UUID;

/**
 * Soft-deletes an agent project owned by the current user.
 */
public interface DeleteAgentProject {

    /**
     * Marks the target project as deleted.
     *
     * @param projectId target project identifier
     */
    void execute(UUID projectId);
}
