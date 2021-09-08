package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.model.user.ConfirmOtpRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OtpMapper {

    OtpMapper INSTANCE = Mappers.getMapper(OtpMapper.class);

    @Mappings({
            @Mapping(source = "otp.status", target = "otpStatus"),
            @Mapping(source = "otp.dateTime", target = "otpCreationDateTime")
    })
    ConfirmOtpRsModel buildOtpResponseModel(Otp otp, User user);
}
