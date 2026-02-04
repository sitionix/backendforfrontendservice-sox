package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResendEmailVerificationApiMapper {

    ResendEmailVerificationResponseDTO asResendEmailVerificationResponseDTO(final ResendEmailVerificationResponse src);
}
