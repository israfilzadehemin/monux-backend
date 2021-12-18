package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Service;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public abstract class ServiceMapper {

    public static ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    @Mappings({
            @Mapping(target = "title.az", source = "titleAz"),
            @Mapping(target = "title.en", source = "titleEn"),
            @Mapping(target = "title.ru", source = "titleRu"),
            @Mapping(target = "text.az", source = "textAz"),
            @Mapping(target = "text.en", source = "textEn"),
            @Mapping(target = "text.ru", source = "textRu")
    })
    public abstract Service buildService(ServiceRqModel request);

    @AfterMapping
    void setExtraFields(@MappingTarget Service.ServiceBuilder service, ServiceRqModel request) {
        service.serviceId(UUID.randomUUID().toString());

        service.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTitleAz())
                .en(request.getTitleEn())
                .ru(request.getTitleRu())
                .build());
        service.text(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTextAz())
                .en(request.getTextEn())
                .ru(request.getTextRu())
                .build());
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract ServiceRsModel buildServiceResponseModel(Service service);

    @AfterMapping
    void setLanguageValuesToResponseModel(@MappingTarget ServiceRsModel.ServiceRsModelBuilder response, Service service) {
        Map<String, String> titles = new HashMap<>();
        Map<String, String> texts = new HashMap<>();

        titles.put("titleAz", service.getTitle().getAz());
        titles.put("titleEn", service.getTitle().getEn());
        titles.put("titleRu", service.getTitle().getRu());

        texts.put("textAz", service.getText().getAz());
        texts.put("textEn", service.getText().getEn());
        texts.put("textRu", service.getText().getRu());

        response.title(titles);
        response.text(texts);
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract ServiceRsModel buildServiceResponseModelWithLanguage(Service service, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget ServiceRsModel.ServiceRsModelBuilder response, Service service, String language) {
        switch (language) {
            case "en" -> {
                response.title(service.getTitle().getEn());
                response.text(service.getText().getEn());
            }
            case "ru" -> {
                response.title(service.getTitle().getRu());
                response.text(service.getText().getRu());
            }
            default -> {
                response.title(service.getTitle().getAz());
                response.text(service.getText().getAz());
            }
        }
    }


}
