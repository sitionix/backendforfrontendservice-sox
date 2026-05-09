package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.ProjectConversationsResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListProjectConversationsImpl implements ListProjectConversations {

    private final AgentConversationClient agentConversationClient;

    @Override
    public ProjectConversationsResponse execute(final UUID projectId) {
        return this.agentConversationClient.listProjectConversations(projectId);
    }
}
