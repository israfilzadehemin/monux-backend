package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.ConfirmOtpRqModel;
import com.budgetmanagementapp.model.ConfirmOtpRsModel;

public interface OtpService {

    ConfirmOtpRsModel confirmOtp(ConfirmOtpRqModel otpRequestModel);
}
