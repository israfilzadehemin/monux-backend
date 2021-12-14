package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Banner;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public abstract class BannerMapper {

    public static BannerMapper INSTANCE = Mappers.getMapper(BannerMapper.class);

    @Mappings({
            @Mapping(target = "title.az", source = "titleAz"),
            @Mapping(target = "title.en", source = "titleEn"),
            @Mapping(target = "title.ru", source = "titleRu"),
            @Mapping(target = "text.az", source = "textAz"),
            @Mapping(target = "text.en", source = "textEn"),
            @Mapping(target = "text.ru", source = "textRu")
    })
    public abstract Banner buildBanner(BannerRqModel request);

    @AfterMapping
    void mapRequestToBanner(@MappingTarget Banner.BannerBuilder banner, BannerRqModel request) {
        banner.bannerId(UUID.randomUUID().toString());
        banner.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .en(request.getTitleEn())
                .az(request.getTitleAz())
                .ru(request.getTitleRu())
                .build());
        banner.text(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .en(request.getTextEn())
                .az(request.getTextAz())
                .ru(request.getTitleRu())
                .build());
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract BannerRsModel buildBannerResponseModel(Banner banner);

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract BannerRsModel buildBannerResponseModelWithLanguage(Banner banner, String language);

    @AfterMapping
    void mapBannerToResponse(@MappingTarget BannerRsModel.BannerRsModelBuilder response, Banner banner, String language) {
        switch (language) {
            case "en" -> {
                response.title(banner.getTitle().getEn());
                response.text(banner.getText().getEn());
            }
            case "ru" -> {
                response.title(banner.getTitle().getRu());
                response.text(banner.getText().getRu());
            }
            default -> {
                response.title(banner.getTitle().getAz());
                response.text(banner.getText().getAz());
            }
        }
    }


}
