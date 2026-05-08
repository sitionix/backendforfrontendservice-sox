package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;

public interface CreateProjectConversation {

    ProjectConversationDetails execute(UUID projectId, CreateProjectConversationRequest request);
}
