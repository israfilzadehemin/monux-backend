package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.mapper.ServiceMapper;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import com.budgetmanagementapp.repository.ServiceRepository;
import com.budgetmanagementapp.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepo;

    @Override
    public List<ServiceRsModel> getAllServices() {
        return serviceRepo.findAll().stream()
                .map(ServiceMapper.INSTANCE::buildServiceResponseModel)
                .collect(Collectors.toList());
    }
}
