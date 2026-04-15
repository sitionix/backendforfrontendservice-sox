package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;

/**
 * Use case for activating one automation agent.
 */
public interface ActivateAgent {

    /**
     * Activates one automation agent.
     *
     * @param agentId agent identifier.
     * @return updated agent.
     */
    Agent execute(UUID agentId);
}
