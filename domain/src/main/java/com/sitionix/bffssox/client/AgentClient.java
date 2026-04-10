package com.sitionix.bffssox.client;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.AgentsResponse;
import com.sitionix.bffssox.domain.CreateAgentRequest;
import java.util.UUID;

/**
 * Downstream automation service client.
 */
public interface AgentClient {

    /**
     * Creates a new automation agent.
     *
     * @param request create payload.
     * @return created agent.
     */
    Agent createAgent(CreateAgentRequest request);

    /**
     * Returns current automation agents.
     *
     * @return persisted agents response.
     */
    AgentsResponse getAgents();

    /**
     * Returns one automation agent.
     *
     * @param agentId unique agent identifier.
     * @return persisted agent.
     */
    Agent getAgent(UUID agentId);
}
