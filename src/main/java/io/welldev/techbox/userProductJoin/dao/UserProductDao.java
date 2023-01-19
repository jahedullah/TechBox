package io.welldev.techbox.userProductJoin.dao;

import io.welldev.techbox.userProductJoin.entity.UserProduct;
import io.welldev.techbox.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserProductDao implements IUserProductDao {

    @Override
    public int getProductQuantityByUserIdAndProductId(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "SELECT quantity FROM UserProduct WHERE userId = :userId AND productId = :productId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        Integer quantity = (Integer) query.uniqueResult();
        session.close();
        return quantity == null ? 0 : quantity;

    }

    @Override
    public boolean isUserAndProductRowExist(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        String hql = "FROM UserProduct WHERE userId = :userId AND productId = :productId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        UserProduct userProduct = (UserProduct) query.uniqueResult();
        session.close();
        if(userProduct == null){
            return false;
        }
        return true;
    }

    @Override
    public void increaseUserProductRowQuantityByOne(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "FROM UserProduct WHERE userId = :userId AND productId = :productId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        UserProduct userProduct = (UserProduct) query.uniqueResult();
        userProduct.setQuantity(userProduct.getQuantity() + 1);
        session.update(userProduct);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void decreaseUserProductRowQuantityByOne(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String hql = "FROM UserProduct WHERE userId = :userId AND productId = :productId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        UserProduct userProduct = (UserProduct) query.uniqueResult();
        userProduct.setQuantity(userProduct.getQuantity() - 1);
        session.update(userProduct);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void setUserProductRowQuantityToOne(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        UserProduct userProduct = getUserProductRow(userId, productId);
        userProduct.setQuantity(1);
        session.update(userProduct);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public UserProduct getUserProductRow(int userId, int productId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        String hql = "FROM UserProduct WHERE userId = :userId AND productId = :productId";
        Query query = session.createQuery(hql);
        query.setParameter("userId", userId);
        query.setParameter("productId", productId);
        UserProduct userProduct = (UserProduct) query.uniqueResult();
        session.close();
        return userProduct;
    }
}
