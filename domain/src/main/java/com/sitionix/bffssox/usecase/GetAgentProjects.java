package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProjectsPageResponse;

public interface GetAgentProjects {

    AgentProjectsPageResponse execute(Integer page, Integer size);
}
