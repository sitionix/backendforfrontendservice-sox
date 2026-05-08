package com.sitionix.bffssox.domain;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectConversationProject {

    private UUID id;

    private String name;

    private String context;
}
