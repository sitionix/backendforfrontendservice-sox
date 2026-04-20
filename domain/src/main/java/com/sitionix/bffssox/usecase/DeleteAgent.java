package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;

/**
 * Use case for soft deleting one automation agent.
 */
public interface DeleteAgent {

    /**
     * Soft deletes one automation agent.
     *
     * @param agentId agent identifier.
     * @return updated agent.
     */
    Agent execute(UUID agentId);
}
