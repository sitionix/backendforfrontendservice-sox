package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectFlowPaletteSource {

    private String sourceType;

    private UUID sourceId;

    private String sourceName;
}
