package io.welldev.techbox.authentication.configuration.otp.dao;

import java.time.LocalDateTime;

import io.welldev.techbox.authentication.configuration.otp.entity.Otp;
import io.welldev.techbox.user.entity.User;

import org.springframework.stereotype.Repository;


public interface OtpDao {
  void saveOtpForUser(User user, String otpValue, LocalDateTime expiryTime);
  void deleteOtpForUser(Otp otp);
  Otp findByUserId(Long userId);
  Otp findByOtpCode(String otpCode);
}
