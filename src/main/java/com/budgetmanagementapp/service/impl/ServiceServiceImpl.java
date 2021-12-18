package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.ServiceNotFoundException;
import com.budgetmanagementapp.mapper.ServiceMapper;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import com.budgetmanagementapp.repository.ServiceRepository;
import com.budgetmanagementapp.service.ServiceService;
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
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepo;

    @Override
    public List<ServiceRsModel> getAllServices(String language) {
        return serviceRepo.findAll().stream()
                .map(service -> ServiceMapper.INSTANCE.buildServiceResponseModelWithLanguage(service, language))
                .collect(Collectors.toList());
    }

    @Override
    public ServiceRsModel createService(ServiceRqModel request) {
        com.budgetmanagementapp.entity.Service service = ServiceMapper.INSTANCE.buildService(request);
        serviceRepo.save(service);
        ServiceRsModel response = ServiceMapper.INSTANCE.buildServiceResponseModel(service);
        log.info(format(SERVICE_CREATED_MSG, response));
        return response;
    }

    @Override
    public ServiceRsModel updateService(ServiceRqModel request, String serviceId) {
        com.budgetmanagementapp.entity.Service service = serviceById(serviceId);
        service.setTitle(Translation.builder()
                .az(request.getTitleAz())
                .en(request.getTitleEn())
                .ru(request.getTitleRu())
                .build());
        service.setText(Translation.builder()
                .az(request.getTextAz())
                .en(request.getTextEn())
                .ru(request.getTextRu())
                .build());
        service.setIcon(request.getIcon());
        serviceRepo.save(service);
        ServiceRsModel response = ServiceMapper.INSTANCE.buildServiceResponseModel(service);
        log.info(format(SERVICE_UPDATED_MSG, response));
        return response;
    }

    @Override
    public ServiceRsModel deleteService(String serviceId) {
        com.budgetmanagementapp.entity.Service service = serviceById(serviceId);
        serviceRepo.delete(service);
        ServiceRsModel response = ServiceMapper.INSTANCE.buildServiceResponseModel(service);
        log.info(format(SERVICE_DELETED_MSG, response));
        return response;
    }

    private com.budgetmanagementapp.entity.Service serviceById(String serviceId) {
        return serviceRepo.findByServiceId(serviceId).orElseThrow(
                () -> new ServiceNotFoundException(format(SERVICE_NOT_FOUND_MSG, serviceId))
        );
    }
}
