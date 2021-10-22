package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Step;
import com.budgetmanagementapp.exception.StepNotFoundException;
import com.budgetmanagementapp.mapper.StepMapper;
import com.budgetmanagementapp.model.home.StepRqModel;
import com.budgetmanagementapp.model.home.StepRsModel;
import com.budgetmanagementapp.repository.StepRepository;
import com.budgetmanagementapp.service.StepService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class StepServiceImpl implements StepService {

    private final StepRepository stepRepo;

    @Override
    public List<StepRsModel> getAllSteps() {
        return stepRepo.findAll().stream()
                .map(StepMapper.INSTANCE::buildStepResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public StepRsModel createStep(StepRqModel request) {
        Step step = StepMapper.INSTANCE.buildStep(request);
        stepRepo.save(step);
        StepRsModel response = StepMapper.INSTANCE.buildStepResponseModel(step);
        log.info(format(STEP_CREATED_MSG, response));
        return response;
    }

    @Override
    public StepRsModel updateStep(StepRqModel request, String stepId) {
        Step step = stepById(stepId);
        step.setTitle(request.getTitle());
        step.setText(request.getText());
        step.setIcon(request.getIcon());
        step.setColor(request.getColor());
        stepRepo.save(step);
        StepRsModel response = StepMapper.INSTANCE.buildStepResponseModel(step);
        log.info(format(STEP_UPDATED_MSG, response));
        return response;
    }

    @Override
    public StepRsModel deleteStep(String stepId) {
        Step step = stepById(stepId);
        stepRepo.delete(step);
        StepRsModel response = StepMapper.INSTANCE.buildStepResponseModel(step);
        log.info(format(STEP_DELETED_MSG, response));
        return response;
    }

    private Step stepById(String stepId) {
        return stepRepo.byId(stepId).orElseThrow(
                () -> new StepNotFoundException(format(STEP_NOT_FOUND_MSG, stepId))
        );
    }
}
