package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.mapper.ServiceMapper.SERVICE_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.ALL_SERVICES_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.SERVICE_CREATED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.SERVICE_DELETED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.SERVICE_NOT_FOUND_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.SERVICE_UPDATED_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.ServiceNotFoundException;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import com.budgetmanagementapp.repository.ServiceRepository;
import com.budgetmanagementapp.service.ServiceService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@AllArgsConstructor
@Service
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepo;

    @Override
    public List<ServiceRsModel> getAllServices(String language) {
        var services = serviceRepo.findAll().stream()
                .map(service -> SERVICE_MAPPER_INSTANCE.buildServiceResponseModelWithLanguage(service, language))
                .collect(Collectors.toList());

        log.info(ALL_SERVICES_MSG, services);
        return services;
    }

    @Override
    public ServiceRsModel createService(ServiceRqModel request) {
        com.budgetmanagementapp.entity.Service service = SERVICE_MAPPER_INSTANCE.buildService(request);
        serviceRepo.save(service);

        ServiceRsModel response = SERVICE_MAPPER_INSTANCE.buildServiceResponseModel(service);

        log.info(SERVICE_CREATED_MSG, response);
        return response;
    }

    @Override
    public ServiceRsModel updateService(ServiceRqModel request, String serviceId) {
        com.budgetmanagementapp.entity.Service service = serviceById(serviceId);
        service.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        service.setText(Translation.builder()
                .az(request.getTextAz()).en(request.getTextEn()).ru(request.getTextRu())
                .build());

        service.setIcon(request.getIcon());
        serviceRepo.save(service);

        ServiceRsModel response = SERVICE_MAPPER_INSTANCE.buildServiceResponseModel(service);

        log.info(SERVICE_UPDATED_MSG, response);
        return response;
    }

    @Override
    public ServiceRsModel deleteService(String serviceId) {
        com.budgetmanagementapp.entity.Service service = serviceById(serviceId);
        serviceRepo.delete(service);

        ServiceRsModel response = SERVICE_MAPPER_INSTANCE.buildServiceResponseModel(service);

        log.info(SERVICE_DELETED_MSG, response);
        return response;
    }

    private com.budgetmanagementapp.entity.Service serviceById(String serviceId) {
        return serviceRepo.findByServiceId(serviceId).orElseThrow(
                () -> new ServiceNotFoundException(format(SERVICE_NOT_FOUND_MSG, serviceId))
        );
    }
}
