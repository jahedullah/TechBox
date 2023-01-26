package io.welldev.techbox.authentication.configuration.jwt.dao;

import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.user.dao.UserDao;
import io.welldev.techbox.user.entity.User;

import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JwtDao implements IJwtDao {
    private final SessionFactory sessionFactory;
    private final UserDao userDao;


    @Override
    public void saveTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken) {
        Session session = sessionFactory.getCurrentSession();
        Jwt jwt = new Jwt();
        jwt.setUser(user);
        jwt.setAccessToken(jwtAccessToken);
        jwt.setRefreshToken(jwtRefreshToken);
        session.save(jwt);
    }

    @Override
    public String getUserEmail(String refreshToken) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Jwt WHERE  refreshToken = :refreshToken";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("refreshToken", refreshToken);
        Jwt jwt = query.uniqueResult();
        User user = userDao.getUser(jwt.getUser().getId());
        return user.getEmail();
    }

    @Override
    public boolean isUserExist(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Jwt jwt = query.uniqueResult();

        return jwt != null;
    }

    @Override
    public void updateTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken) {
        Session session = sessionFactory.getCurrentSession();
        Jwt jwtToUpdate = getTheRowOfJwt(user.getId());
        jwtToUpdate.setAccessToken(jwtAccessToken);
        jwtToUpdate.setRefreshToken(jwtRefreshToken);
        session.update(jwtToUpdate);
    }

    @Override
    public void updateAccessTokenForUser(User user, String newAccessToken) {
        Session session = sessionFactory.getCurrentSession();
        Jwt jwtToUpdate = getTheRowOfJwt(user.getId());
        jwtToUpdate.setAccessToken(newAccessToken);
        session.update(jwtToUpdate);

    }

    @Override
    public Jwt getTheRowOfJwt(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        return query.uniqueResult();
    }

}
