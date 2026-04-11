package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAgentRequest {

    private String name;

    private String description;
}
