package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.EmailVerificationDTO;
import com.app_afesox.bffssox.api_first.dto.EmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailVerificationApiMapper {

    EmailVerificationRequest asEmailVerificationRequest(final EmailVerificationDTO src);

    EmailVerificationResponseDTO asEmailVerificationResponseDTO(final EmailVerificationResponse src);
}
