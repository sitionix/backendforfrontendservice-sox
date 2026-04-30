package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ChatExecutionFailureDTO;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import java.util.Map;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatExecutionFailureApiMapper {

    default ChatExecutionFailureDTO asChatExecutionFailureDto(final ChatExecutionFailure src) {
        if (src == null) {
            return null;
        }
        return ChatExecutionFailureDTO.builder()
                .code(src.getFailureClass())
                .message(src.getReason())
                .details(src.getRetryable() == null ? null : Map.of("retryable", src.getRetryable()))
                .build();
    }
}
