package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.PatchAgentRequest;
import java.util.UUID;

/**
 * Use case for patching one automation agent identity.
 */
public interface PatchAgent {

    /**
     * Applies partial update for one automation agent.
     *
     * @param agentId agent identifier.
     * @param request partial update payload.
     * @return updated agent.
     */
    Agent execute(UUID agentId, PatchAgentRequest request);
}
