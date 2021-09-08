package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.dateTime", target = "creationDateTime")
    UserRsModel buildUserResponseModel(User user);

    CreatePasswordRsModel buildPasswordResponseModel(CreatePasswordRqModel requestBody);

    ResetPasswordRsModel buildResetPasswordResponseModel(String username, ResetPasswordRqModel requestBody);


}
