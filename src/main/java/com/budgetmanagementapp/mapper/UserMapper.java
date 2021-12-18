package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper {

    public static UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.dateTime", target = "creationDateTime")
    public abstract UserRsModel buildUserResponseModel(User user);

    public abstract CreatePasswordRsModel buildPasswordResponseModel(CreatePasswordRqModel requestBody);

    public abstract ResetPasswordRsModel buildResetPasswordResponseModel(String username, ResetPasswordRqModel requestBody);

    public abstract UserInfoRsModel buildUserInfoResponseModel(User user);

}
