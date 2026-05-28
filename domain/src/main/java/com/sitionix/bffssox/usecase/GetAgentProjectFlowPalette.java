package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import java.util.UUID;

public interface GetAgentProjectFlowPalette {

    AgentProjectFlowPaletteResponse execute(UUID projectId);
}
