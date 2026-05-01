package com.sitionix.bffssox.mapper;

import com.app_afesox.atmssox.client.dto.ExecutionStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatExecutionStatusClientMapper {

    default String mapExecutionStatus(final ExecutionStatusDTO value) {
        if (value == null) {
            return null;
        }
        return switch (value.getValue()) {
            case "ACCEPTED" -> "QUEUED";
            case "IN_PROGRESS" -> "IN_PROGRESS";
            case "SUCCEEDED" -> "COMPLETED";
            case "FAILED" -> "FAILED";
            default -> value.getValue();
        };
    }
}
