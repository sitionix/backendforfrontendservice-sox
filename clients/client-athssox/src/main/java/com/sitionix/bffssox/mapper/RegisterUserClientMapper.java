package com.sitionix.bffssox.mapper;

import com.app_afesox.athssox.client.dto.RegisterUserDTO;
import com.app_afesox.athssox.client.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserClientMapper {

    RegisterUserDTO asRegisterUserDto(RegisterUserRequest src);

    RegisterUserResponse asRegisterUserResponse(ResponseRegisterUserDTO src);
}
