package com.sitionix.bffssox.usecase;

import java.util.UUID;

public interface RemoveAgentFromProject {

    void execute(UUID projectId, UUID agentId);
}
