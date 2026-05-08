package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentConversationClient;
import com.sitionix.bffssox.domain.CreateProjectConversationRequest;
import com.sitionix.bffssox.domain.ProjectConversationDetails;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateProjectConversationImpl implements CreateProjectConversation {

    private final AgentConversationClient agentConversationClient;

    @Override
    public ProjectConversationDetails execute(final UUID projectId, final CreateProjectConversationRequest request) {
        return this.agentConversationClient.createProjectConversation(projectId, request);
    }
}
