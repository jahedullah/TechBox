package io.welldev.techbox.user.dao;


import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserPassChangeRequestDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import io.welldev.techbox.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface IUserDao {
    User getUser(int userId);

    User updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto);

    void updateUserPassword(int userId, String newPassword);

    User deleteUser(int userId);

    void save(User user);

    User findByEmail(String email);

    List<UserDto> getUserList();


    void deleteById(int uid);


    void addProduct(User user, Product product);

    Set<Product> productList(User user);

    Product productDeleteFromUser(User user, int productId);
}
