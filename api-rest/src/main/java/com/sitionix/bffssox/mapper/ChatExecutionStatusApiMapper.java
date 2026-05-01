package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ExecutionStatusDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChatExecutionStatusApiMapper {

    default ExecutionStatusDTO mapExecutionStatus(final String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "QUEUED" -> ExecutionStatusDTO.ACCEPTED;
            case "COMPLETED" -> ExecutionStatusDTO.SUCCEEDED;
            default -> ExecutionStatusDTO.fromValue(value);
        };
    }
}
