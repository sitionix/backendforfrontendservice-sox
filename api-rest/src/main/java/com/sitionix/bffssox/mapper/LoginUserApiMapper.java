package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.LoginRequestDTO;
import com.app_afesox.bffssox.api_first.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginUserApiMapper {

    LoginRequest asLoginRequest(final LoginRequestDTO src);

    LoginResponseDTO asLoginResponseDTO(final LoginResponse src);
}
