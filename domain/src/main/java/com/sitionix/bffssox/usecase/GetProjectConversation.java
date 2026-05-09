package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;

/**
 * Retrieves one project-bound conversation shell through BFF.
 */
public interface GetProjectConversation {

    /**
     * Returns project conversation details by project and conversation identifiers.
     *
     * @param projectId target project identifier
     * @param conversationId project conversation identifier
     * @return project conversation details
     */
    ProjectConversationDetails execute(UUID projectId, UUID conversationId);
}
