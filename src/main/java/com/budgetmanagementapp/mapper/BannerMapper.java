package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Banner;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class BannerMapper {

    public static BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);

    public abstract Banner buildBanner(BannerRqModel request);

    @AfterMapping
    void mapBannerId(@MappingTarget Banner.BannerBuilder banner) {
        banner.bannerId(UUID.randomUUID().toString());
    }

    public abstract BannerRsModel buildBannerResponseModel(Banner banner);

}
