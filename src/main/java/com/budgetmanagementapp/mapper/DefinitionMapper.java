package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Definition;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public abstract class DefinitionMapper {

    public static DefinitionMapper INSTANCE = Mappers.getMapper(DefinitionMapper.class);

    @Mappings({
            @Mapping(target = "title.az", source = "titleAz"),
            @Mapping(target = "title.en", source = "titleEn"),
            @Mapping(target = "title.ru", source = "titleRu"),
            @Mapping(target = "text.az", source = "textAz"),
            @Mapping(target = "text.en", source = "textEn"),
            @Mapping(target = "text.ru", source = "textRu")
    })
    public abstract Definition buildDefinition(DefinitionRqModel request);

    @AfterMapping
    void setExtraFields(@MappingTarget Definition.DefinitionBuilder definition, DefinitionRqModel request) {
        definition.definitionId(UUID.randomUUID().toString());

        definition.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getTitleAz())
                .en(request.getTitleEn())
                .ru(request.getTitleRu())
                .build());
        definition.text(Translation.builder()
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
    public abstract DefinitionRsModel buildDefinitionRsModel(Definition definition);

    @AfterMapping
    void setLanguageValuesToResponseModel(@MappingTarget DefinitionRsModel.DefinitionRsModelBuilder response, Definition definition) {
        Map<String, String> titles = new HashMap<>();
        Map<String, String> texts = new HashMap<>();

        titles.put("titleAz", definition.getTitle().getAz());
        titles.put("titleEn", definition.getTitle().getEn());
        titles.put("titleRu", definition.getTitle().getRu());

        texts.put("textAz", definition.getText().getAz());
        texts.put("textEn", definition.getText().getEn());
        texts.put("textRu", definition.getText().getRu());

        response.title(titles);
        response.text(texts);
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract DefinitionRsModel buildDefinitionRsModelWithLanguage(Definition definition, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget DefinitionRsModel.DefinitionRsModelBuilder response, Definition definition, String language) {
        switch (language) {
            case "en" -> {
                response.title(definition.getTitle().getEn());
                response.text(definition.getText().getEn());
            }
            case "ru" -> {
                response.title(definition.getTitle().getRu());
                response.text(definition.getText().getRu());
            }
            default -> {
                response.title(definition.getTitle().getAz());
                response.text(definition.getText().getAz());
            }
        }
    }

}
