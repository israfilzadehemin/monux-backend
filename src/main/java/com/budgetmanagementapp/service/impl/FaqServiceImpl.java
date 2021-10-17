package com.budgetmanagementapp.service.impl;

import com.budgetmanagementapp.entity.Faq;
import com.budgetmanagementapp.exception.FaqNotFoundException;
import com.budgetmanagementapp.mapper.FaqMapper;
import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import com.budgetmanagementapp.model.faq.UpdateFaqRqModel;
import com.budgetmanagementapp.repository.FaqRepository;
import com.budgetmanagementapp.service.FaqService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.budgetmanagementapp.utility.MsgConstant.*;
import static java.lang.String.format;

@Log4j2
@Service
@AllArgsConstructor
public class FaqServiceImpl implements FaqService {

    private final FaqRepository faqRepo;

    @Override
    public List<FaqRsModel> getAllFaqs() {
        return faqRepo.findAll().stream()
                .map(FaqMapper.INSTANCE::buildFaqResponseModel)
                .collect(Collectors.toList());
    }

    @Override
    public FaqRsModel getFaqById(String faqId) {
        Faq faq = findById(faqId);
        return FaqMapper.INSTANCE.buildFaqResponseModel(faq);
    }

    @Override
    public FaqRsModel createFaq(FaqRqModel request) {
        Faq faq = FaqMapper.INSTANCE.buildFaq(request);
        faqRepo.save(faq);
        FaqRsModel response = FaqMapper.INSTANCE.buildFaqResponseModel(faq);
        log.info(format(FAQ_CREATED_MSG, response));
        return response;
    }

    @Override
    public FaqRsModel updateFaq(UpdateFaqRqModel request) {
        Faq faq = findById(request.getFaqId());
        faq.setQuestion(request.getQuestion());
        faq.setAnswer(request.getAnswer());
        faqRepo.save(faq);
        FaqRsModel response = FaqMapper.INSTANCE.buildFaqResponseModel(faq);
        log.info(format(FAQ_UPDATED_MSG, response));
        return response;
    }

    @Override
    public FaqRsModel deleteFaq(String faqId) {
        Faq faq = findById(faqId);
        faqRepo.delete(faq);
        FaqRsModel response = FaqMapper.INSTANCE.buildFaqResponseModel(faq);
        log.info(format(FAQ_DELETED_MSG, response));
        return response;
    }

    private Faq findById(String faqId){
        return faqRepo.byId(faqId).orElseThrow(
                () -> new FaqNotFoundException(format(FAQ_NOT_FOUND_MSG, faqId)));
    }
}
