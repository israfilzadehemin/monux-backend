package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;

import java.util.List;

public interface DefinitionService {

    List<DefinitionRsModel> getAllDefinitions(String language);

    DefinitionRsModel createDefinition(DefinitionRqModel request);

    DefinitionRsModel updateDefinition(DefinitionRqModel request, String definitionId);

    DefinitionRsModel deleteDefinition(String definitionId);
}
