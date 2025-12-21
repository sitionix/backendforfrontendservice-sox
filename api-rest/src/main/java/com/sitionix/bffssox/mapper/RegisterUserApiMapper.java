package com.sitionix.bffssox.mapper;

import com.app_afesox.bffssox.api_first.dto.RegisterUserDTO;
import com.app_afesox.bffssox.api_first.dto.ResponseRegisterUserDTO;
import com.sitionix.bffssox.domain.RegisterUserRequest;
import com.sitionix.bffssox.domain.RegisterUserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegisterUserApiMapper {

    RegisterUserRequest asRegisterUserRequest(RegisterUserDTO src);

    ResponseRegisterUserDTO asResponseRegisterUserDto(RegisterUserResponse src);
}
