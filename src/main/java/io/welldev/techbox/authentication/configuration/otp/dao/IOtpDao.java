package io.welldev.techbox.authentication.configuration.otp.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.welldev.techbox.authentication.configuration.otp.entity.Otp;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IOtpDao implements OtpDao{

  private final SessionFactory sessionFactory;

  @Override
  public void saveOtpForUser(User user, String otpValue, LocalDateTime expiryTime) {
    Session session = sessionFactory.getCurrentSession();
    Otp otp =  new Otp();
    otp.setOtpValue(otpValue);
    otp.setUser(user);
    otp.setExpiryTime(LocalDateTime.from(expiryTime));
    session.save(otp);
  }

  @Override
  public void deleteOtpForUser(Otp otp) {

  }

  @Override
  public Otp findByUserId(Long userId) {
    return null;
  }

  @Override
  public Otp findByOtpCode(String otpCode) {
    return null;
  }
}
