package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.ResendEmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.ResendEmailVerificationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResendEmailVerificationClientMapper {

    ResendEmailVerificationResponse asResendEmailVerificationResponse(final ResendEmailVerificationResponseDTO src);
}
