package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;

/**
 * Use case for archiving one automation agent.
 */
public interface ArchiveAgent {

    /**
     * Archives one automation agent.
     *
     * @param agentId agent identifier.
     * @return updated agent.
     */
    Agent execute(UUID agentId);
}
