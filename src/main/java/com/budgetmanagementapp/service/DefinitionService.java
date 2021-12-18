package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.definition.DefinitionRqModel;
import com.budgetmanagementapp.model.definition.DefinitionRsModel;
import com.budgetmanagementapp.model.definition.UpdateDefinitionRqModel;

import java.util.List;

public interface DefinitionService {

    List<DefinitionRsModel> getAllDefinitions(String language);

    DefinitionRsModel createDefinition(DefinitionRqModel request);

    DefinitionRsModel updateDefinition(UpdateDefinitionRqModel request);

    DefinitionRsModel deleteDefinition(String definitionId);
}
