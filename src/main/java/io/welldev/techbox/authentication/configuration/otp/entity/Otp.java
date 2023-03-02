
package io.welldev.techbox.authentication.configuration.otp.entity;
import java.time.LocalDateTime;

import io.welldev.techbox.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Otp {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long otpId;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
  private String otpValue;

  private LocalDateTime expiryTime;

  // Method to check if the OTP is expired or not
  public boolean isExpired() {
    return LocalDateTime.now().isAfter(this.expiryTime);
  }



}
