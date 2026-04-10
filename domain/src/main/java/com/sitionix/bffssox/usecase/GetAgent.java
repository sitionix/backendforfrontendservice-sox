package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import java.util.UUID;

public interface GetAgent {

    Agent execute(UUID agentId);
}
