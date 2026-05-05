package com.sitionix.bffssox.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentProjectsPageResponse {

    private List<AgentProject> items;

    private Integer page;

    private Integer size;

    private Boolean hasNext;
}
