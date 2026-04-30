package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ChatExecutionFailureDTO;
import com.sitionix.bffssox.domain.ChatExecutionFailure;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatExecutionFailureApiMapper {

    @Mapping(target = "code", source = "failureClass")
    @Mapping(target = "message", source = "reason")
    @Mapping(target = "details", expression = "java(src.getRetryable() == null ? null : java.util.Map.of(\"retryable\", src.getRetryable()))")
    ChatExecutionFailureDTO asChatExecutionFailureDto(ChatExecutionFailure src);
}
