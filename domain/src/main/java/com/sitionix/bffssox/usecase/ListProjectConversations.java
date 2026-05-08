package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import java.util.UUID;

public interface ListProjectConversations {

    ProjectConversationsResponse execute(UUID projectId);
}
