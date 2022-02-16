package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Banner;
import com.budgetmanagementapp.entity.Translation;
import com.budgetmanagementapp.exception.DataNotFoundException;
import com.budgetmanagementapp.model.banner.BannerRqModel;
import com.budgetmanagementapp.model.banner.BannerRsModel;
import com.budgetmanagementapp.repository.BannerRepository;
import com.budgetmanagementapp.service.BannerService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.budgetmanagementapp.mapper.BannerMapper.BANNER_MAPPER_INSTANCE;
import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@AllArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepo;

    @Override
    public BannerRsModel getBannerById(String bannerId, String language) {
        return BANNER_MAPPER_INSTANCE.buildBannerResponseModelWithLanguage(bannerById(bannerId), language);
    }

    @Override
    public BannerRsModel getBannerByKeyword(String keyword, String language) {
        var banner = BANNER_MAPPER_INSTANCE.buildBannerResponseModelWithLanguage(
                bannerRepo.byKeyword(keyword)
                        .orElseThrow(() -> new DataNotFoundException(format(BANNER_NOT_FOUND_MSG, keyword), 6000)), language);

        log.info(BANNER_BY_KEYWORD_MSG, keyword, banner);
        return banner;
    }

    @Override
    public BannerRsModel createBanner(BannerRqModel request) {
        Banner banner = BANNER_MAPPER_INSTANCE.buildBanner(request);
        bannerRepo.save(banner);

        BannerRsModel response = BANNER_MAPPER_INSTANCE.buildBannerResponseModel(banner);

        log.info(BANNER_CREATED_MSG, response);
        return response;
    }

    @Override
    public BannerRsModel updateBanner(BannerRqModel request, String bannerId) {
        Banner banner = bannerById(bannerId);

        banner.setTitle(Translation.builder()
                .az(request.getTitleAz()).en(request.getTitleEn()).ru(request.getTitleRu())
                .build());

        banner.setText(Translation.builder()
                .az(request.getTextAz()).en(request.getTextEn()).ru(request.getTextRu())
                .build());

        banner.setKeyword(request.getKeyword());
        banner.setImage(request.getImage());
        bannerRepo.save(banner);

        BannerRsModel response = BANNER_MAPPER_INSTANCE.buildBannerResponseModel(banner);

        log.info(BANNER_UPDATED_MSG, response);
        return response;
    }

    @Override
    public BannerRsModel deleteBanner(String bannerId) {
        Banner banner = bannerById(bannerId);
        bannerRepo.delete(banner);

        BannerRsModel response = BANNER_MAPPER_INSTANCE.buildBannerResponseModel(banner);

        log.info(BANNER_DELETED_MSG, response);
        return response;
    }

    private Banner bannerById(String bannerId) {
        return bannerRepo.byBannerId(bannerId).orElseThrow(
                () -> new DataNotFoundException(format(BANNER_NOT_FOUND_MSG, bannerId), 6000));
    }

}
