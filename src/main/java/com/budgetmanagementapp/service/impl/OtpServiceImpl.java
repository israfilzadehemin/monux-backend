package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_CONFIRMED;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.Constant.STATUS_USED;
import static com.budgetmanagementapp.utility.MsgConstant.EXPIRED_OTP_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_OTP_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.OTP_CONFIRMED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_BY_OTP_NOT_FOUND_MSG;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.ExpiredOtpException;
import com.budgetmanagementapp.exception.InvalidOtpException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.model.ConfirmOtpRequestModel;
import com.budgetmanagementapp.model.ConfirmOtpResponseModel;
import com.budgetmanagementapp.repository.OtpRepository;
import com.budgetmanagementapp.repository.UserRepository;
import com.budgetmanagementapp.service.OtpService;
import java.time.LocalDateTime;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class OtpServiceImpl implements OtpService {
    private final OtpRepository otpRepo;
    private final UserRepository userRepo;


    @Override
    @Transactional
    public ConfirmOtpResponseModel confirmOtp(ConfirmOtpRequestModel otpRequestModel) {
        Otp otp = otpRepo.findByOtp(otpRequestModel.getOtp())
                .orElseThrow(() -> new InvalidOtpException(INVALID_OTP_MSG));

        checkOtpAvailability(otp);

        if (otp.getUser().getUsername().equals(otpRequestModel.getUsername())) {
            User user = userRepo.findByOtp(otp).orElseThrow(
                    () -> new UserNotFoundException(String.format(USER_BY_OTP_NOT_FOUND_MSG, otp.getOtp())));

            otp.setStatus(STATUS_USED);
            otpRepo.save(otp);
            user.setStatus(STATUS_CONFIRMED);
            userRepo.save(user);

            log.info(String.format(OTP_CONFIRMED_MSG, otp.getUser().getUsername()));

            return ConfirmOtpResponseModel.builder()
                    .otpId(otp.getOtpId())
                    .username(user.getUsername())
                    .otpStatus(otp.getStatus())
                    .otpCreationDateTime(otp.getDateTime())
                    .build();
        } else {
            throw new InvalidOtpException(INVALID_OTP_MSG);
        }
    }

    private void checkOtpAvailability(Otp otp) {
        if (otp.getDateTime().isBefore(LocalDateTime.now().minusMinutes(2))
                || !otp.getStatus().equals(STATUS_NEW)) {
            throw new ExpiredOtpException(EXPIRED_OTP_MSG);
        }
    }
}
