package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.faq.FaqRqModel;
import com.budgetmanagementapp.model.faq.FaqRsModel;
import com.budgetmanagementapp.model.faq.UpdateFaqRqModel;

import java.util.List;

public interface FaqService {

    List<FaqRsModel> getAllFaqs();

    FaqRsModel getFaqById(String faqId);

    FaqRsModel createFaq(FaqRqModel request);

    FaqRsModel updateFaq(UpdateFaqRqModel request);

    FaqRsModel deleteFaq(String faqId);
}