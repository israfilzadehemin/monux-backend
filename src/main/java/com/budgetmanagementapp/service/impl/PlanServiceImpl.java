package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Feature;
import com.budgetmanagementapp.entity.Plan;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.model.plan.PlanRqModel;
import com.budgetmanagementapp.model.plan.PlanRsModel;
import com.budgetmanagementapp.repository.FeatureRepository;
import com.budgetmanagementapp.repository.PlanRepository;
import com.budgetmanagementapp.service.PlanService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.mapper.PlanMapper.PLAN_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepo;
    private final FeatureRepository featureRepo;

    @Override
    public List<PlanRsModel> getAllPlans(String language) {
        var plans = planRepo.findAll()
                .stream()
                .map(plan -> PLAN_MAPPER_INSTANCE.buildPlanResponseModelWithLanguage(plan, language))
                .collect(Collectors.toList());

        log.info(ALL_PLANS_MSG, plans);
        return plans;
    }

    @Override
    public PlanRsModel addPlan(PlanRqModel request) {
        List<Feature> features = featureRepo.allByFeatureIds(request.getFeaturesIds());
        Plan plan = PLAN_MAPPER_INSTANCE.buildPlan(request, features);
        planRepo.save(plan);

        PlanRsModel planRsModel = PLAN_MAPPER_INSTANCE.buildPlanResponseModel(plan);

        log.info(PLAN_CREATED_MSG, planRsModel);
        return planRsModel;
    }

    @Override
    public PlanRsModel updatePlan(PlanRqModel request, String planId) {
        Plan plan = planRepo.byPlanId(planId)
                .orElseThrow(() -> new DataNotFoundException(format(PLAN_NOT_FOUND_MSG, planId), 6006));

        plan.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTextEn()).ru(request.getTitleRu())
                .build());

        plan.setText(Translation.builder()
                .az(request.getTextAz()).en(request.getTextEn()).ru(request.getTextRu())
                .build());

        plan.setPrice(request.getPrice());
        plan.setPeriodType(request.getPeriodType());
        planRepo.save(plan);

        PlanRsModel planRsModel = PLAN_MAPPER_INSTANCE.buildPlanResponseModel(plan);
        log.info(PLAN_UPDATED_MSG, planRsModel);
        return planRsModel;
    }

    @Override
    public PlanRsModel deletePlan(String planId) {
        Plan plan = planRepo.byPlanId(planId).orElseThrow(
                () -> new DataNotFoundException(format(PLAN_NOT_FOUND_MSG, planId), 6006));
        planRepo.delete(plan);

        PlanRsModel planRsModel = PLAN_MAPPER_INSTANCE.buildPlanResponseModel(plan);
        log.info(PLAN_DELETED_MSG, planRsModel);
        return planRsModel;
    }
}
