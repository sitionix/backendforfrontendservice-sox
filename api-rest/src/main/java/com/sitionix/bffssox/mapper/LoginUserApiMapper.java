package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.SessionResponse;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface LoginUserApiMapper {

    LoginRequest asLoginRequest(final LoginRequestDTO src);

    LoginResponseDTO asLoginResponseDTO(final SessionResponse src);

    default OffsetDateTime asOffsetDateTime(final Instant value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }
}
