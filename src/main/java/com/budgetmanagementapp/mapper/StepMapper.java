package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Step;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.model.home.StepRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public abstract class StepMapper {

    public static StepMapper INSTANCE = Mappers.getMapper(StepMapper.class);

    @Mappings({
            @Mapping(target = "title.az", source = "titleAz"),
            @Mapping(target = "title.en", source = "titleEn"),
            @Mapping(target = "title.ru", source = "titleRu"),
            @Mapping(target = "text.az", source = "textAz"),
            @Mapping(target = "text.en", source = "textEn"),
            @Mapping(target = "text.ru", source = "textRu")
    })
    public abstract Step buildStep(StepRqModel request);

    @AfterMapping
    void setExtraFields(@MappingTarget Step.StepBuilder step, StepRqModel request) {
        step.stepId(UUID.randomUUID().toString());
        step.title(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .en(request.getTitleEn())
                .az(request.getTitleAz())
                .ru(request.getTitleRu())
                .build());
        step.text(Translation.builder()
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
    public abstract StepRsModel buildStepResponseModel(Step step);

    @AfterMapping
    void setLanguageValuesToResponseModel(@MappingTarget StepRsModel.StepRsModelBuilder response, Step step) {
        Map<String, String> titles = new HashMap<>();
        Map<String, String> texts = new HashMap<>();

        titles.put("titleAz", step.getTitle().getAz());
        titles.put("titleEn", step.getTitle().getEn());
        titles.put("titleRu", step.getTitle().getRu());

        texts.put("textAz", step.getText().getAz());
        texts.put("textEn", step.getText().getEn());
        texts.put("textRu", step.getText().getRu());

        response.title(titles);
        response.text(texts);
    }

    @Mappings({
            @Mapping(target = "title", ignore = true),
            @Mapping(target = "text", ignore = true)
    })
    public abstract StepRsModel buildStepResponseModelWithLanguage(Step step, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget StepRsModel.StepRsModelBuilder response, Step step, String language) {
        switch (language) {
            case "en" -> {
                response.title(step.getTitle().getEn());
                response.text(step.getText().getEn());
            }
            case "ru" -> {
                response.title(step.getTitle().getRu());
                response.text(step.getText().getRu());
            }
            default -> {
                response.title(step.getTitle().getAz());
                response.text(step.getText().getAz());
            }
        }
    }
}
