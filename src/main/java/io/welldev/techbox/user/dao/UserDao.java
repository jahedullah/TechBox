package io.welldev.techbox.user.dao;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.entity.User;

import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private final IProductDao productDao;
    private final SessionFactory sessionFactory;

    @Override
    public User getUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, userId);
    }


    @Override
    public User updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto) {
        Session session = sessionFactory.getCurrentSession();
        User userToUpdate = session.get(User.class, userId);
        userToUpdate.setFirstName(userUpdateRequestDto.getFirstName());
        userToUpdate.setLastName(userUpdateRequestDto.getLastName());
        userToUpdate.setEmail(userUpdateRequestDto.getEmail());
        userToUpdate.setMobileNumber(userUpdateRequestDto.getMobileNumber());
        session.update(userToUpdate);
        return userToUpdate;
    }

    @Override
    public void updateUserPassword(int userId, String newPassword) {
        Session session = sessionFactory.getCurrentSession();
        User userToUpdate = session.get(User.class, userId);
        userToUpdate.setPassword(new BCryptPasswordEncoder().encode(newPassword));
        session.update(userToUpdate);

    }

    @Override
    public User deleteUser(int userId) {
        Session session = sessionFactory.getCurrentSession();
        User userToDelete = session.get(User.class, userId);
        session.delete(userToDelete);
        return userToDelete;
    }


    @Override
    public void save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @Transactional
    public User findByEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        String query = "from User where email = :e";
        Query<User> q = session.createQuery(query);
        q.setParameter("e", email);
        return q.uniqueResult();
    }

    @Override
    public List<UserDto> getUserList() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Query<User> query = session.createQuery(criteriaQuery);
        List<User> productList = query.getResultList();
        List<UserDto> newUserList = new ArrayList<>();
        productList.forEach(
                tempUser -> {
                    UserDto userDto
                            = new UserDto(
                            tempUser.getId(),
                            tempUser.getFirstName(),
                            tempUser.getLastName(),
                            tempUser.getEmail(),
                            tempUser.getMobileNumber(),
                            tempUser.getUserType());
                    newUserList.add(userDto);
                }
        );
        return newUserList;
    }


    @Override
    public void deleteById(int uid) {
        Session session = sessionFactory.getCurrentSession();
        String query = "from User where id = :id";
        Query<User> q = session.createQuery(query);
        q.setParameter("id", uid);
        User user = q.uniqueResult();
        session.delete(user);
    }

    @Override
    public void addProduct(User user, Product product) {
        Session session = sessionFactory.getCurrentSession();
        user.getProductList().add(product);
        session.update(user);
    }


    public Set<Product> productList(User user) {
        return user.getProductList();
    }

    public Product productDeleteFromUser(User user, int productId) {
        Set<Product> productList = user.getProductList().stream().filter(
                product -> product.getId() != productId).collect(Collectors.toSet());
        user.setProductList(productList);
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        return productDao.getProduct(productId);
    }

}
