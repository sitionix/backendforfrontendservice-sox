package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetProjectConversationImpl implements GetProjectConversation {

    private final AgentConversationClient agentConversationClient;

    @Override
    public ProjectConversationDetails execute(final UUID projectId, final UUID conversationId) {
        return this.agentConversationClient.getProjectConversation(projectId, conversationId);
    }
}
