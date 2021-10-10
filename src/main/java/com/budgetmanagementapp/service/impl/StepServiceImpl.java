package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.mapper.StepMapper;
import com.budgetmanagementapp.model.home.StepRsModel;
import com.budgetmanagementapp.repository.StepRepository;
import com.budgetmanagementapp.service.StepService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
}
