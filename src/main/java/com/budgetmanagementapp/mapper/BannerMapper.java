package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Banner;
import com.budgetmanagementapp.model.home.BannerRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class BannerMapper {

    public static BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);

    public abstract BannerRsModel buildBannerResponseModel(Banner banner);

}
