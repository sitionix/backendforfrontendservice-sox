package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;

public interface GetProjectConversation {

    ProjectConversationDetails execute(UUID projectId, UUID conversationId);
}
