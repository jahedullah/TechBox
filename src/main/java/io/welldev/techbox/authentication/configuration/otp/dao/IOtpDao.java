package io.welldev.techbox.authentication.configuration.otp.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;

import io.welldev.techbox.authentication.configuration.otp.entity.Otp;
import io.welldev.techbox.user.dao.UserDao;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class IOtpDao implements OtpDao{

  private final SessionFactory sessionFactory;
  private final UserDao userDao;

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
  public Otp getOtpRow(String otpValue) {
    Session session = sessionFactory.getCurrentSession();
    String hql = "FROM Otp WHERE otpValue = :otpValue";
    Query<Otp> query = session.createQuery(hql);
    query.setParameter("otpValue", otpValue);
    return query.uniqueResult();
  }

  @Override
  public void deleteOtpForUser(Otp otp) {

  }

  @Override
  public User findByUserId(int userId) {
    return userDao.getUser(userId);
  }

  @Override
  public Otp findByOtpCode(String otpCode) {
    return null;
  }
}
