package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatchAgentRequest {

    private String name;

    private String description;
}
