package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Service;
import com.budgetmanagementapp.model.home.ServiceRqModel;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ServiceMapper {

    public static ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    public abstract Service buildService(ServiceRqModel request);

    @AfterMapping
    void mapServiceId(@MappingTarget Service.ServiceBuilder service) {
        service.serviceId(UUID.randomUUID().toString());
    }

    public abstract ServiceRsModel buildServiceResponseModel(Service service);

}
