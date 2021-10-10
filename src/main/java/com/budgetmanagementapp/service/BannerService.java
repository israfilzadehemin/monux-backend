package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.BannerRsModel;

public interface BannerService {

    BannerRsModel getBannerById(String bannerId);

    BannerRsModel getBannerByKeyword(String keyword);


}
