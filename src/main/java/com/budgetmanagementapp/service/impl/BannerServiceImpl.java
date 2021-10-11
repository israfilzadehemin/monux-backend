package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Banner;
import com.budgetmanagementapp.exception.BannerNotFoundException;
import com.budgetmanagementapp.mapper.BannerMapper;
import com.budgetmanagementapp.model.home.BannerRsModel;
import com.budgetmanagementapp.repository.BannerRepository;
import com.budgetmanagementapp.service.BannerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.budgetmanagementapp.utility.MsgConstant.BANNER_NOT_FOUND_MSG;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepo;

    @Override
    public BannerRsModel getBannerById(String bannerId) {
        Banner banner = bannerRepo.byBannerId(bannerId).orElseThrow(
                () -> new BannerNotFoundException(format(BANNER_NOT_FOUND_MSG, bannerId)));
        return BannerMapper.INSTANCE.buildBannerResponseModel(banner);
    }

    @Override
    public BannerRsModel getBannerByKeyword(String keyword) {
        Banner banner = bannerRepo.byKeyword(keyword).orElseThrow(
                () -> new BannerNotFoundException(format(BANNER_NOT_FOUND_MSG, keyword)));
        return BannerMapper.INSTANCE.buildBannerResponseModel(banner);
    }
}
