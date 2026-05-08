package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchAgentProjectRequest {

    private String name;

    private String description;

    private String context;
}
