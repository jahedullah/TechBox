package io.welldev.techbox.authentication.configuration.jwt.dao;

import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class JwtDao implements IJwtDao {


    @Override
    public void saveTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Jwt jwt = new Jwt();
        jwt.setUser(user);
        jwt.setAccessToken(jwtAccessToken);
        jwt.setRefreshToken(jwtRefreshToken);
        session.save(jwt);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean isUserExist(int userId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Jwt jwt = query.uniqueResult();
        session.close();
        return jwt != null;
    }

    @Override
    public void updateTokenForUser(User user, String jwtAccessToken) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        Jwt jwtToUpdate = getTheRowOfJwt(user.getId());
        jwtToUpdate.setAccessToken(jwtAccessToken);
        session.update(jwtToUpdate);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public Jwt getTheRowOfJwt(int userId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Jwt jwt = query.uniqueResult();
        session.close();
        return jwt;
    }

}
