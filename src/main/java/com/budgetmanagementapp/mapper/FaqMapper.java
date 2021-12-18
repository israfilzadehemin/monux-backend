package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Faq;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mapper
public abstract class FaqMapper {

    public static FaqMapper INSTANCE = Mappers.getMapper(FaqMapper.class);

    @Mappings({
            @Mapping(target = "question.az", source = "questionAz"),
            @Mapping(target = "question.en", source = "questionEn"),
            @Mapping(target = "question.ru", source = "questionRu"),
            @Mapping(target = "answer.az", source = "answerAz"),
            @Mapping(target = "answer.en", source = "answerEn"),
            @Mapping(target = "answer.ru", source = "answerRu")
    })
    public abstract Faq buildFaq(FaqRqModel request);

    @AfterMapping
    void setExtraFields(@MappingTarget Faq.FaqBuilder faq, FaqRqModel request) {
        faq.faqId(UUID.randomUUID().toString());

        faq.question(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getQuestionAz())
                .en(request.getQuestionEn())
                .ru(request.getQuestionRu())
                .build());
        faq.answer(Translation.builder()
                .translationId(UUID.randomUUID().toString())
                .az(request.getAnswerAz())
                .en(request.getAnswerEn())
                .ru(request.getAnswerRu())
                .build());
    }

    @Mappings({
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "answer", ignore = true)
    })
    public abstract FaqRsModel buildFaqResponseModel(Faq faq);

    @AfterMapping
    void setLanguageValuesToResponseModel(@MappingTarget FaqRsModel.FaqRsModelBuilder response, Faq faq) {
        Map<String, String> questions = new HashMap<>();
        Map<String, String> answers = new HashMap<>();

        questions.put("questionAz", faq.getQuestion().getAz());
        questions.put("questionEn", faq.getQuestion().getEn());
        questions.put("questionRu", faq.getQuestion().getRu());

        answers.put("answerAz", faq.getAnswer().getAz());
        answers.put("answerEn", faq.getAnswer().getEn());
        answers.put("answerRu", faq.getAnswer().getRu());

        response.question(questions);
        response.answer(answers);
    }

    @Mappings({
            @Mapping(target = "question", ignore = true),
            @Mapping(target = "answer", ignore = true)
    })
    public abstract FaqRsModel buildFaqResponseModelWithLanguage(Faq faq, String language);

    @AfterMapping
    void mapLanguageBasedFields(@MappingTarget FaqRsModel.FaqRsModelBuilder response, Faq faq, String language) {
        switch (language) {
            case "en" -> {
                response.question(faq.getQuestion().getEn());
                response.answer(faq.getAnswer().getEn());
            }
            case "ru" -> {
                response.question(faq.getQuestion().getRu());
                response.answer(faq.getAnswer().getRu());
            }
            default -> {
                response.question(faq.getQuestion().getAz());
                response.answer(faq.getAnswer().getAz());
            }
        }
    }
}
