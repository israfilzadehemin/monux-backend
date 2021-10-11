package com.budgetmanagementapp.mapper;

import com.budgetmanagementapp.entity.Service;
import com.budgetmanagementapp.model.home.ServiceRsModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ServiceMapper {

    public static ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);

    public abstract ServiceRsModel buildServiceResponseModel(Service service);

}
