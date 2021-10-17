package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;

public interface BannerService {

    BannerRsModel getBannerById(String bannerId);

    BannerRsModel getBannerByKeyword(String keyword);

    BannerRsModel createBanner(BannerRqModel request);

    BannerRsModel updateBanner(BannerRqModel request, String bannerId);

    BannerRsModel deleteBanner(String bannerId);


}
