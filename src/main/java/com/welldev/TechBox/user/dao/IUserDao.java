package com.welldev.TechBox.user.dao;


import com.welldev.TechBox.product.entity.Product;
import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserUpdateRequestDto;
import com.welldev.TechBox.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IUserDao {
    User getUser(int userId);
    User updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto);

    User deleteUser(int userId);
    User findByUsername(String username);

    void save(User user);

    User findByEmail(String email);

    List<UserDto> getUserList();

    List findAllEmail();

    void deleteByEmail(String email);
    void deleteById(int uid);

//    String buyProductByID(int id, HttpServletRequest request);

    void addProduct(User user, Product product);

    List<Product> productList(User user);

    Product productDeleteFromUser(User user, int productId);
}
