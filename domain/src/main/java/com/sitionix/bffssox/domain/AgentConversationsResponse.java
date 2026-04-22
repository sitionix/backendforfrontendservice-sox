package com.sitionix.bffssox.domain;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AgentConversationsResponse {

    private List<AgentConversation> items;
}
