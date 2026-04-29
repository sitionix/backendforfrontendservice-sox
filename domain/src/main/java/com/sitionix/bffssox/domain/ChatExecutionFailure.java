package com.sitionix.bffssox.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatExecutionFailure {

    private String failureClass;

    private String reason;

    private Boolean retryable;
}
