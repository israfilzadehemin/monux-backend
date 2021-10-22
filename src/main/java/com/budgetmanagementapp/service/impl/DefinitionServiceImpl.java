package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Definition;
import com.budgetmanagementapp.exception.DefinitionNotFoundException;
import com.budgetmanagementapp.mapper.DefinitionMapper;
import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.model.definition.UpdateDefinitionRqModel;
import com.budgetmanagementapp.repository.DefinitionRepository;
import com.budgetmanagementapp.service.DefinitionService;
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
public class DefinitionServiceImpl implements DefinitionService {

    private final DefinitionRepository definitionRepo;

    @Override
    public List<DefinitionRsModel> getAllDefinitions() {
        return definitionRepo.findAll().stream()
                .map(DefinitionMapper.INSTANCE::buildDefinitionRsModel)
                .collect(Collectors.toList());
    }

    @Override
    public DefinitionRsModel createDefinition(DefinitionRqModel request) {
        Definition definition = DefinitionMapper.INSTANCE.buildDefinition(request);
        definitionRepo.save(definition);
        DefinitionRsModel response = DefinitionMapper.INSTANCE.buildDefinitionRsModel(definition);
        log.info(format(DEFINITION_CREATED_MSG, response));
        return response;
    }

    @Override
    public DefinitionRsModel updateDefinition(UpdateDefinitionRqModel request) {
        Definition definition = findById(request.getDefinitionId());
        definition.setTitle(request.getTitle());
        definition.setText(request.getText());
        definition.setIcon(request.getIcon());
        definitionRepo.save(definition);
        DefinitionRsModel response = DefinitionMapper.INSTANCE.buildDefinitionRsModel(definition);
        log.info(format(DEFINITION_UPDATED_MSG, response));
        return response;
    }

    @Override
    public DefinitionRsModel deleteDefinition(String definitionId) {
        Definition definition = findById(definitionId);
        definitionRepo.delete(definition);
        DefinitionRsModel response = DefinitionMapper.INSTANCE.buildDefinitionRsModel(definition);
        log.info(format(DEFINITION_DELETED_MSG, response));
        return response;
    }

    private Definition findById(String definitionId) {
        return definitionRepo.byDefinitionId(definitionId).orElseThrow(
                () -> new DefinitionNotFoundException(format(DEFINITION_NOT_FOUND_MSG, definitionId))
        );
    }
}
