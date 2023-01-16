package com.welldev.TechBox.model.dao;


import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component
public interface UserDao {
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

    List<Product> productsList(HttpServletRequest request);

    void productsDeleteById(int pid, HttpServletRequest request);
}
