package io.welldev.techbox.authentication.configuration.otp.service;

import java.time.LocalDateTime;

import io.welldev.techbox.authentication.configuration.otp.dto.OtpGenerateRequest;

public interface OtpService {
  void generateOtp(OtpGenerateRequest otpGenerateRequest, int length);
  boolean verifyOtp(String otpValue);
  LocalDateTime generateExpiryTime(int minutes);
  boolean isTimeExpired(LocalDateTime time);
}
