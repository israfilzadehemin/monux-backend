package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.mapper.DefinitionMapper;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.repository.DefinitionRepository;
import com.budgetmanagementapp.service.DefinitionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class DefinitionServiceImpl implements DefinitionService {

    private final DefinitionRepository definitionRepo;

    @Override
    public List<DefinitionRsModel> getAllDefinitions() {
        return definitionRepo.findAll().stream()
                .map(DefinitionMapper.INSTANCE::buildDefinitionRsModel)
                .collect(Collectors.toList());
    }
}
