package io.welldev.techbox.user.dao;

import io.welldev.techbox.authentication.configuration.jwt.JwtTokenFilter;
import io.welldev.techbox.authentication.configuration.jwt.service.JwtService;
import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.utils.HibernateUtils;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class UserDao implements IUserDao {

    private final IProductDao productDao;

    private final JwtService jwtService;

    @Override
    public User getUser(int userId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, userId);
        session.getTransaction().commit();
        session.close();

        return user;
    }

    @Override
    public User updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        User userToUpdate  = session.get(User.class, userId);
        userToUpdate.setFirstname(userUpdateRequestDto.getFirstname());
        userToUpdate.setLastname(userUpdateRequestDto.getLastname());
        userToUpdate.setEmail(userUpdateRequestDto.getEmail());
        userToUpdate.setMobilenumber(userUpdateRequestDto.getMobileNumber());

        session.beginTransaction();
        session.update(userToUpdate);
        session.getTransaction().commit();
        session.close();
        return userToUpdate;
    }

    @Override
    public User deleteUser(int userId) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        User userToDelete = session.get(User.class, userId);
        session.beginTransaction();
        session.delete(userToDelete);
        session.getTransaction().commit();
        session.close();

        return userToDelete;
    }

    public User findByUsername(String Username) {

        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        User user = session.get(User.class, Username);
        session.getTransaction().commit();
        session.close();

        return user;

    }

    @Override
    public void save(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public User findByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from User where email = :e";
        Query q = session.createQuery(query);
        q.setParameter("e", email);
        User user = (User) q.uniqueResult();
        session.getTransaction().commit();
        session.close();

        return user;

    }

    @Override
    public List<UserDto> getUserList() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        Query<User> query = session.createQuery(criteriaQuery);
        List<User> productList = query.getResultList();
        List<UserDto> newUserList = new ArrayList<>();
        productList.forEach(
                (tempUser) -> {
                    UserDto userDto
                            = new UserDto(
                            tempUser.getId(),
                            tempUser.getFirstname(),
                            tempUser.getLastname(),
                            tempUser.getEmail(),
                            tempUser.getMobilenumber(),
                            tempUser.getUsertype());
                    newUserList.add(userDto);
                }
        );
        session.close();

        return newUserList;
    }

    @Override
    public List findAllEmail() {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "select email from User";
        Query q = session.createQuery(query);
        ArrayList<String> emailList = (ArrayList<String>) q.list();
        session.getTransaction().commit();
        session.close();

        return emailList;
    }

    @Override
    public void deleteByEmail(String email) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        User user = findByEmail(email);
        int userId = user.getId();
        User userToDelete = session.get(User.class, userId);
        session.beginTransaction();
        session.delete(userToDelete);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void deleteById(int uid) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "from User where id = :id";
        Query q = session.createQuery(query);
        q.setParameter("id", uid);
        User user = (User) q.uniqueResult();
        session.delete(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void addProduct(User user, Product product) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        user.getProductList().add(product);
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }


    public List<Product> productList(User user) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        List<Product> productList = new ArrayList<>(user.getProductList());
        session.close();

        return productList;
    }

    public Product productDeleteFromUser(User user, int productId) {
        List<Product> productList = user.getProductList().stream().filter(
                product -> product.getId() != productId).collect(Collectors.toList());
        user.setProductList(productList);
        Session session = HibernateUtils.getSessionFactory().openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();

        return productDao.getProduct(productId);

    }

}
