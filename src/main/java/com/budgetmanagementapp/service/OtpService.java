package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.ConfirmOtpRequestModel;
import com.budgetmanagementapp.model.ConfirmOtpResponseModel;

public interface OtpService {

    ConfirmOtpResponseModel confirmOtp(ConfirmOtpRequestModel otpRequestModel);
}
