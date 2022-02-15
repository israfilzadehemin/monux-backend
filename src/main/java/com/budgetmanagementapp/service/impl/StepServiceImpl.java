package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Step;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.model.step.StepRqModel;
import com.budgetmanagementapp.model.step.StepRsModel;
import com.budgetmanagementapp.repository.StepRepository;
import com.budgetmanagementapp.service.StepService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.StepMapper.STEP_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepo;

    @Override
    public List<StepRsModel> getAllSteps(String language) {
        var steps = stepRepo.findAll()
                .stream()
                .map(step -> STEP_MAPPER_INSTANCE.buildStepResponseModelWithLanguage(step, language))
                .collect(Collectors.toList());

        log.info(ALL_STEPS_MSG, steps);
        return steps;
    }

    @Override
    public StepRsModel createStep(StepRqModel request) {
        Step step = STEP_MAPPER_INSTANCE.buildStep(request);
        stepRepo.save(step);

        StepRsModel stepRsModel = STEP_MAPPER_INSTANCE.buildStepResponseModel(step);

        log.info(STEP_CREATED_MSG, stepRsModel);
        return stepRsModel;
    }

    @Override
    public StepRsModel updateStep(StepRqModel request, String stepId) {
        Step step = stepById(stepId);

        step.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        step.setText(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        step.setIcon(request.getIcon());
        step.setColor(request.getColor());
        stepRepo.save(step);

        StepRsModel stepRsModel = STEP_MAPPER_INSTANCE.buildStepResponseModel(step);

        log.info(STEP_UPDATED_MSG, stepRsModel);
        return stepRsModel;
    }

    @Override
    public StepRsModel deleteStep(String stepId) {
        Step step = stepById(stepId);
        stepRepo.delete(step);

        StepRsModel stepRsModel = STEP_MAPPER_INSTANCE.buildStepResponseModel(step);

        log.info(STEP_DELETED_MSG, stepRsModel);
        return stepRsModel;
    }

    private Step stepById(String stepId) {
        return stepRepo.byId(stepId).orElseThrow(
                () -> new DataNotFoundException(format(STEP_NOT_FOUND_MSG, stepId), 6008)
        );
    }
}
