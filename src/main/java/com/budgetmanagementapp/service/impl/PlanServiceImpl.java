package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Plan;
import com.budgetmanagementapp.exception.PlanNotFoundException;
import com.budgetmanagementapp.mapper.PlanMapper;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.model.plan.UpdatePlanRqModel;
import com.budgetmanagementapp.repository.FeatureRepository;
import com.budgetmanagementapp.repository.PlanRepository;
import com.budgetmanagementapp.service.PlanService;
import com.budgetmanagementapp.utility.CustomFormatter;
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
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepo;
    private final FeatureRepository featureRepo;

    @Override
    public List<PlanRsModel> getAllPlans() {
        return planRepo.findAll().stream()
                .map(PlanMapper.INSTANCE::buildPlanResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public PlanRsModel addPlan(PlanRqModel request) {
        List<Feature> features = featureRepo.allByFeatureIds(request.getFeaturesIds());
        Plan plan = PlanMapper.INSTANCE.buildPlan(request, features);
        planRepo.save(plan);
        PlanRsModel response = PlanMapper.INSTANCE.buildPlanResponseModel(plan);
        log.info(format(PLAN_CREATED_MSG, response));
        return response;
    }

    @Override
    public PlanRsModel updatePlan(UpdatePlanRqModel request) {
        Plan plan = planRepo.byPlanId(request.getPlanId())
                .orElseThrow(() -> new PlanNotFoundException(format(PLAN_NOT_FOUND_MSG, request.getPlanId())));
        plan.setTitle(request.getTitle());
        plan.setText(request.getText());
        plan.setPrice(request.getPrice());
        plan.setPeriodType(CustomFormatter.stringToLocalDate(request.getPeriodType()));
        planRepo.save(plan);
        PlanRsModel response = PlanMapper.INSTANCE.buildPlanResponseModel(plan);
        log.info(format(PLAN_UPDATED_MSG, response));
        return response;
    }
}
