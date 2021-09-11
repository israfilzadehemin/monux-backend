package com.budgetmanagementapp.service;

import com.budgetmanagementapp.model.user.ConfirmOtpRqModel;
import com.budgetmanagementapp.model.user.ConfirmOtpRsModel;

public interface OtpService {

    ConfirmOtpRsModel confirmOtp(ConfirmOtpRqModel otpRequestModel);
}
