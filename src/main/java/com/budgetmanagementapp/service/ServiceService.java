package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.service.ServiceRqModel;
import com.budgetmanagementapp.model.service.ServiceRsModel;
import com.budgetmanagementapp.model.service.UpdateServiceRqModel;

import java.util.List;

public interface ServiceService {

    List<ServiceRsModel> getAllServices(String language);

    ServiceRsModel createService(ServiceRqModel request);

    ServiceRsModel updateService(UpdateServiceRqModel request);

    ServiceRsModel deleteService(String serviceId);
}
