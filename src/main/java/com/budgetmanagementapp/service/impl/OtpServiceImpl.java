package com.budgetmanagementapp.service.impl;

import static com.budgetmanagementapp.utility.Constant.STATUS_CONFIRMED;
import static com.budgetmanagementapp.utility.Constant.STATUS_NEW;
import static com.budgetmanagementapp.utility.Constant.STATUS_USED;
import static com.budgetmanagementapp.utility.MsgConstant.EXPIRED_OTP_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.INVALID_OTP_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.OTP_CONFIRMED_MSG;
import static com.budgetmanagementapp.utility.MsgConstant.USER_BY_OTP_NOT_FOUND_MSG;
import static java.lang.String.format;

import com.budgetmanagementapp.entity.Otp;
import com.budgetmanagementapp.entity.User;
import com.budgetmanagementapp.exception.ExpiredOtpException;
import com.budgetmanagementapp.exception.InvalidOtpException;
import com.budgetmanagementapp.exception.UserNotFoundException;
import com.budgetmanagementapp.mapper.OtpMapper;
import com.budgetmanagementapp.model.user.ConfirmOtpRqModel;
import com.budgetmanagementapp.model.user.ConfirmOtpRsModel;
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
    public ConfirmOtpRsModel confirmOtp(ConfirmOtpRqModel requestBody) {
        Otp otp = otpByValue(requestBody);
        checkOtpAvailability(otp);

        if (otp.getUser().getUsername().equals(requestBody.getUsername())) {
            User user = userByOtp(otp);
            updateOtpAndUserValues(otp, user);

            log.info(format(OTP_CONFIRMED_MSG, otp.getUser().getUsername()));
            return OtpMapper.INSTANCE.buildOtpResponseModel(otp, user);
        } else {
            throw new InvalidOtpException(INVALID_OTP_MSG);
        }
    }

    private Otp otpByValue(ConfirmOtpRqModel requestBody) {
        return otpRepo.findByOtp(requestBody.getOtp()).orElseThrow(() -> new InvalidOtpException(INVALID_OTP_MSG));
    }

    private User userByOtp(Otp otp) {
        return userRepo.byOtp(otp).orElseThrow(
                () -> new UserNotFoundException(format(USER_BY_OTP_NOT_FOUND_MSG, otp.getOtp())));
    }

    private void updateOtpAndUserValues(Otp otp, User user) {
        otp.setStatus(STATUS_USED);
        otpRepo.save(otp);

        user.setStatus(STATUS_CONFIRMED);
        userRepo.save(user);
    }

    private void checkOtpAvailability(Otp otp) {
        if (otp.getDateTime().isBefore(LocalDateTime.now().minusMinutes(2)) || !otp.getStatus().equals(STATUS_NEW)) {
            throw new ExpiredOtpException(EXPIRED_OTP_MSG);
        }
    }
}
