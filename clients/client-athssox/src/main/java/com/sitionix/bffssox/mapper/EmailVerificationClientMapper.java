package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.EmailVerificationDTO;
import com.app_afesox.athssox.client.dto.EmailVerificationResponseDTO;
import com.sitionix.bffssox.domain.EmailVerificationRequest;
import com.sitionix.bffssox.domain.EmailVerificationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailVerificationClientMapper {

    EmailVerificationDTO asEmailVerificationDto(EmailVerificationRequest src);

    EmailVerificationResponse asEmailVerificationResponse(EmailVerificationResponseDTO src);
}
