package io.welldev.techbox.authentication.configuration.jwt.dao;

import io.welldev.techbox.authentication.configuration.jwt.entity.Jwt;
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


    @Override
    public void saveTokenForUser(User user, String jwtAccessToken, String jwtRefreshToken) {
Session session = sessionFactory.getCurrentSession();
//beginTransaction
        Jwt jwt = new Jwt();
        jwt.setUser(user);
        jwt.setAccessToken(jwtAccessToken);
        jwt.setRefreshToken(jwtRefreshToken);
        session.save(jwt);
//commitTransaction
//closeTransaction
    }

    @Override
    public boolean isUserExist(int userId) {
Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Jwt jwt = query.uniqueResult();
//closeTransaction
        return jwt != null;
    }

    @Override
    public void updateTokenForUser(User user, String jwtAccessToken) {
Session session = sessionFactory.getCurrentSession();
//beginTransaction
        Jwt jwtToUpdate = getTheRowOfJwt(user.getId());
        jwtToUpdate.setAccessToken(jwtAccessToken);
        session.update(jwtToUpdate);
//commitTransaction
//closeTransaction
    }

    @Override
    public Jwt getTheRowOfJwt(int userId) {
Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Jwt WHERE  user.id = :userId";
        Query<Jwt> query = session.createQuery(hql);
        query.setParameter("userId", userId);
        Jwt jwt = query.uniqueResult();
//closeTransaction
        return jwt;
    }

}
