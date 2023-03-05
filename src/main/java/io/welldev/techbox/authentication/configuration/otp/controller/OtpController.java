package io.welldev.techbox.authentication.configuration.otp.controller;

import io.welldev.techbox.authentication.configuration.otp.dto.OtpGenerateRequest;
import io.welldev.techbox.authentication.configuration.otp.dto.OtpVerifyRequest;
import io.welldev.techbox.authentication.configuration.otp.service.OtpService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {


  private final OtpService otpService;

  @PostMapping("/generate")
  public ResponseEntity<?> generateOtp(@RequestBody OtpGenerateRequest otpGenerateRequest) {
    try {
      otpService.generateOtp(otpGenerateRequest, 6);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  @GetMapping("/verify")
  public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest otpVerifyRequest) {
    try {
      boolean isVerified = otpService.verifyOtp(otpVerifyRequest.getOtpValue());
      if (isVerified) {
        return ResponseEntity.ok().build();
      } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}






