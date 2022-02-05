package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.banner.BannerRqModel;
import com.budgetmanagementapp.model.banner.BannerRsModel;
import com.budgetmanagementapp.model.banner.UpdateBannerRqModel;

public interface BannerService {

    BannerRsModel getBannerById(String bannerId, String language);

    BannerRsModel getBannerByKeyword(String keyword, String language);

    BannerRsModel createBanner(BannerRqModel request);

    BannerRsModel updateBanner(UpdateBannerRqModel request);

    BannerRsModel deleteBanner(String bannerId);


}
