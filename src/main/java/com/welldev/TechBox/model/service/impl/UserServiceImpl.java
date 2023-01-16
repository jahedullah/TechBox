package com.welldev.TechBox.model.service.impl;

import com.welldev.TechBox.model.dao.ProductDao;
import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final ProductDao productDao;

    @Override
    public UserDto getSingleUser(int userId) {
        Optional<User> user = Optional.ofNullable(userDao.getUser(userId));
        return user.map(value -> new UserDto(
                value.getId(),
                value.getFirstname(),
                value.getLastname(),
                value.getEmail(),
                value.getMobilenumber(),
                value.getUsertype())).orElse(null);
    }

    @Override
    public List<UserDto> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public UserDto updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userDao.getUser(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(user.getEmail(), authentication.getName())) {
            User userToUpdate = userDao.updateUser(userId, userUpdateRequestDto);
            return new UserDto(
                    userToUpdate.getId(),
                    userToUpdate.getFirstname(),
                    userToUpdate.getLastname(),
                    userToUpdate.getEmail(),
                    userToUpdate.getMobilenumber(),
                    userToUpdate.getUsertype()
            );
        } else {
            return null;
        }
    }

    @Override
    public UserDto deleteUser(int userId) {
        User user = userDao.getUser(userId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(user.getEmail(), authentication.getName())) {
            User userToDelete = userDao.deleteUser(userId);
            return new UserDto(
                    userToDelete.getId(),
                    userToDelete.getFirstname(),
                    userToDelete.getLastname(),
                    userToDelete.getEmail(),
                    userToDelete.getMobilenumber(),
                    userToDelete.getUsertype()
            );
        }else {
            return null;
        }
    }

    @Override
    public ProductDto addProduct(int userId, int productId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            User user = userDao.getUser(userId);
            Product product = productDao.getProduct(productId);
            userDao.addProduct(user, product);
            productDao.updateProductUserList(product, user);
            productDao.updateProductCount(product);
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getProductCount()
            );
        }else {
            return null;
        }

    }

}
