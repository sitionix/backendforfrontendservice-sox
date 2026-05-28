package com.sitionix.bffssox.usecase;

import com.sitionix.bffssox.client.AgentProjectClient;
import com.sitionix.bffssox.domain.AgentProjectFlowPaletteResponse;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAgentProjectFlowPaletteImpl implements GetAgentProjectFlowPalette {

    private final AgentProjectClient agentProjectClient;

    @Override
    public AgentProjectFlowPaletteResponse execute(final UUID projectId) {
        return this.agentProjectClient.getAgentProjectFlowPalette(projectId);
    }
}
