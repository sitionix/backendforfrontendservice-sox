package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import java.util.UUID;

/**
 * Lists project-bound conversation shells exposed by BFF for a project.
 */
public interface ListProjectConversations {

    /**
     * Returns project conversation list for the given project.
     *
     * @param projectId target project identifier
     * @return project conversation list response
     */
    ProjectConversationsResponse execute(UUID projectId);
}
