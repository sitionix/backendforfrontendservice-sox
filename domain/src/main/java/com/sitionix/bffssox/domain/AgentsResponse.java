package com.sitionix.bffssox.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentsResponse {

    private List<Agent> items;
}
