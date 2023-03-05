package io.welldev.techbox.authentication.configuration.otp.dto;

import javax.validation.constraints.Size;

import lombok.Getter;

@Getter
public class OtpVerifyRequest {
  @Size(max = 20, message = "Invalid OTP size")
  private String otpValue;
}
