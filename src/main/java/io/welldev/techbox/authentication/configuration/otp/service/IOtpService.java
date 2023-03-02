package io.welldev.techbox.authentication.configuration.otp.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

import javax.transaction.Transactional;

import io.welldev.techbox.authentication.configuration.otp.dao.OtpDao;
import io.welldev.techbox.authentication.configuration.otp.dto.OtpGenerateRequest;
import io.welldev.techbox.constant.MESSAGE;
import io.welldev.techbox.exceptionHandler.UserExistException;
import io.welldev.techbox.user.dao.UserDao;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.user.service.EmailSenderService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IOtpService implements OtpService{

  private final OtpDao otpDao;
  private final UserDao userDao;
  private final EmailSenderService emailSenderService;

  @Transactional
  @Override
  public void generateOtp(OtpGenerateRequest otpGenerateRequest, int length) {
    Optional<User> user =  Optional.ofNullable(userDao.findByEmail(otpGenerateRequest.getEmail()));
    if(user.isPresent()) {

      String numbers = "0123456789";
      Random random = new Random();
      StringBuilder sb = new StringBuilder(length);
      for (int i = 0; i < length; i++) {
        int index = random.nextInt(numbers.length());
        sb.append(numbers.charAt(index));
      }
      otpDao.saveOtpForUser(user.get(), sb.toString() ,generateExpiryTime(2));
      emailSenderService.sendEmail(otpGenerateRequest.getEmail(), "TechBox User OTP", "Your Otp : " + sb.toString());
    }else {
      throw new UserExistException(MESSAGE.USER_NOT_FOUND_WITH_EMAIL);
    }

  }

  @Transactional
  @Override
  public boolean validateOtp(String otpValue) {
    return false;
  }

  @Transactional
  @Override
  public LocalDateTime generateExpiryTime(int minutes) {
    return LocalDateTime.now().plus(minutes, ChronoUnit.MINUTES);
  }
}
