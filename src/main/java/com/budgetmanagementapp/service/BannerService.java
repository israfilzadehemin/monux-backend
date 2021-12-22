package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.home.BannerRqModel;
import com.budgetmanagementapp.model.home.BannerRsModel;

public interface BannerService {

    BannerRsModel getBannerById(String bannerId, String language);

    BannerRsModel getBannerByKeyword(String keyword, String language);

    BannerRsModel createBanner(BannerRqModel request);

    BannerRsModel updateBanner(BannerRqModel request, String bannerId);

    BannerRsModel deleteBanner(String bannerId);


}
