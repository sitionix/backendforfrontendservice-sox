package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.LoginRequestDTO;
import com.app_afesox.athssox.client.dto.LoginResponseDTO;
import com.sitionix.bffssox.domain.LoginRequest;
import com.sitionix.bffssox.domain.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginUserClientMapper {

    LoginRequestDTO asLoginRequestDto(LoginRequest src);

    LoginResponse asLoginResponse(LoginResponseDTO src);
}
