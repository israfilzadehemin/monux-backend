package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Definition;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
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

import static com.budgetmanagementapp.mapper.DefinitionMapper.DEFINITION_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class DefinitionServiceImpl implements DefinitionService {

    private final DefinitionRepository definitionRepo;

    @Override
    public List<DefinitionRsModel> getAllDefinitions(String language) {
        var definitions = definitionRepo.findAll()
                .stream()
                .map(definition -> DEFINITION_MAPPER_INSTANCE.buildDefinitionRsModelWithLanguage(definition, language))
                .collect(Collectors.toList());

        log.info(ALL_DEFINITIONS, definitions);
        return definitions;
    }

    @Override
    public DefinitionRsModel createDefinition(DefinitionRqModel request) {
        Definition definition = DEFINITION_MAPPER_INSTANCE.buildDefinition(request);
        definitionRepo.save(definition);

        DefinitionRsModel response = DEFINITION_MAPPER_INSTANCE.buildDefinitionRsModel(definition);

        log.info(DEFINITION_CREATED_MSG, response);
        return response;
    }

    @Override
    public DefinitionRsModel updateDefinition(UpdateDefinitionRqModel request) {
        Definition definition = findById(request.getDefinitionId());
        definition.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        definition.setText(Translation.builder()
                .az(request.getTextAz()).en(request.getTextEn()).ru(request.getTextRu())
                .build());

        definition.setIcon(request.getIcon());
        definitionRepo.save(definition);

        DefinitionRsModel response = DEFINITION_MAPPER_INSTANCE.buildDefinitionRsModel(definition);

        log.info(DEFINITION_UPDATED_MSG, response);
        return response;
    }

    @Override
    public DefinitionRsModel deleteDefinition(String definitionId) {
        Definition definition = findById(definitionId);
        definitionRepo.delete(definition);

        DefinitionRsModel response = DEFINITION_MAPPER_INSTANCE.buildDefinitionRsModel(definition);

        log.info(DEFINITION_DELETED_MSG, response);
        return response;
    }

    private Definition findById(String definitionId) {
        return definitionRepo.byDefinitionId(definitionId).orElseThrow(
                () -> new DataNotFoundException(format(DEFINITION_NOT_FOUND_MSG, definitionId), 6002)
        );
    }
}
