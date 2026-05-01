package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ChatExecutionFailureDTO;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatExecutionFailureClientMapper {

    default ChatExecutionFailure asChatExecutionFailure(final ChatExecutionFailureDTO src) {
        if (src == null) {
            return null;
        }
        final Object retryable = src.getDetails() == null ? null : src.getDetails().get("retryable");
        return ChatExecutionFailure.builder()
                .failureClass(src.getCode())
                .reason(src.getMessage())
                .retryable(retryable instanceof Boolean value ? value : null)
                .build();
    }
}
