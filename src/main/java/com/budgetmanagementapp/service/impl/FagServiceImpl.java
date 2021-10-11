package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Fag;
import com.budgetmanagementapp.exception.FagNotFoundException;
import com.budgetmanagementapp.mapper.FagMapper;
import com.budgetmanagementapp.model.home.FagRsModel;
import com.budgetmanagementapp.repository.FagRepository;
import com.budgetmanagementapp.service.FagService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.FAG_NOT_FOUND_MSG;
import static java.lang.String.format;

@Service
@AllArgsConstructor
public class FagServiceImpl implements FagService {

    private final FagRepository fagRepo;

    @Override
    public List<FagRsModel> getAllFags() {
        return fagRepo.findAll().stream()
                .map(FagMapper.INSTANCE::buildFagResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public FagRsModel getFagById(String fagId) {
        Fag fag = fagRepo.byId(fagId).orElseThrow(
                () -> new FagNotFoundException(format(FAG_NOT_FOUND_MSG, fagId)));
        return FagMapper.INSTANCE.buildFagResponseModel(fag);
    }
}
