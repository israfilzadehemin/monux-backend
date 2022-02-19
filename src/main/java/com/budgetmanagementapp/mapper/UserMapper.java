package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper {

    public static UserMapper USER_MAPPER_INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "user.dateTime", target = "creationDateTime")
    @Mapping(source = "user.paymentDate", target = "paymentDate")
    public abstract UserRsModel buildUserResponseModel(User user);

    public abstract CreatePasswordRsModel buildPasswordResponseModel(CreatePasswordRqModel requestBody);

    public abstract ResetPasswordRsModel buildResetPasswordResponseModel(String username, ResetPasswordRqModel requestBody);

    @Mapping(target = "creationDate", source = "user.dateTime")
    @Mapping(target = "paymentDate", source = "user.paymentDate")
    public abstract UserInfoRsModel buildUserInfoResponseModel(User user);

}
