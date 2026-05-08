package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;

/**
 * Creates a project-bound conversation shell through BFF orchestration.
 */
public interface CreateProjectConversation {

    /**
     * Creates a new project conversation with selected agents.
     *
     * @param projectId target project identifier
     * @param request selected agent identifiers
     * @return created project conversation details
     */
    ProjectConversationDetails execute(UUID projectId, CreateProjectConversationRequest request);
}
