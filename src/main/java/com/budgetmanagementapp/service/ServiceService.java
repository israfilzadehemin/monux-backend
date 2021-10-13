package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;

import java.util.List;

public interface ServiceService {

    List<ServiceRsModel> getAllServices();

    ServiceRsModel createService(ServiceRqModel request);

    ServiceRsModel updateService(ServiceRqModel request, String serviceId);

    ServiceRsModel deleteService(String serviceId);
}
