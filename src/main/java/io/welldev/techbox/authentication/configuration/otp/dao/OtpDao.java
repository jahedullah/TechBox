package io.welldev.techbox.authentication.configuration.otp.dao;

import java.time.LocalDateTime;

import io.welldev.techbox.authentication.configuration.otp.entity.Otp;
import io.welldev.techbox.user.entity.User;



public interface OtpDao {
  void saveOtpForUser(User user, String otpValue, LocalDateTime expiryTime);
  Otp getOtpRow(String otpValue);
  void deleteOtpForUser(Otp otp);
  User findByUserId(int userId);
  Otp findByOtpCode(String otpCode);
}
