package io.welldev.techbox.authentication.configuration.otp.dto;

import javax.validation.constraints.Pattern;

public class OtpVerifyRequest {

  @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
      + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
      message = "Email is not valid.")
  private String email;

  private String otpValue;
}
