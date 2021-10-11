package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.FagRsModel;

import java.util.List;

public interface FagService {

    List<FagRsModel> getAllFags();

    FagRsModel getFagById(String fagId);
}
