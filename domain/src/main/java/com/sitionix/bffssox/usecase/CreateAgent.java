package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.Agent;
import com.sitionix.bffssox.domain.CreateAgentRequest;

public interface CreateAgent {

    Agent execute(CreateAgentRequest request);
}
