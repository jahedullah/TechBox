package io.welldev.techbox.user.service;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.dao.IUserDao;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import io.welldev.techbox.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IProductDao productDao;

    @Override
    public UserDto getSingleUser(int userId) {
        Optional<User> user = Optional.ofNullable(userDao.getUser(userId));
        return user.map(value -> new UserDto(
                value.getId(),
                value.getFirstName(),
                value.getLastName(),
                value.getEmail(),
                value.getMobileNumber(),
                value.getUserType())).orElse(null);
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
                    userToUpdate.getFirstName(),
                    userToUpdate.getLastName(),
                    userToUpdate.getEmail(),
                    userToUpdate.getMobileNumber(),
                    userToUpdate.getUserType()
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
                    userToDelete.getFirstName(),
                    userToDelete.getLastName(),
                    userToDelete.getEmail(),
                    userToDelete.getMobileNumber(),
                    userToDelete.getUserType()
            );
        } else {
            return null;
        }
    }

    @Override
    public ProductDto addProduct(int userId, int productId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            Product product = productDao.getProduct(productId);
            User user = userDao.getUser(userId);
            userDao.addProduct(user, product);
            productDao.addUser(product, user);
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice());
        } else {
            return null;
        }
    }

    @Override
    public UserProductDto productDeleteById(int userId, int productId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            Product product = productDao.getProduct(productId);
            User user = userDao.getUser(userId);
            userDao.productDeleteFromUser(user, productId);

            return new UserProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice());
        } else {
            return null;
        }
    }

    @Override
    public Set<UserProductDto> productList(int userId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            Set<Product> productList = userDao.productList(userDao.getUser(userId));
            Set<UserProductDto> newProductList = new HashSet<>();
            productList.forEach(
                    (tempProduct) -> {

                        UserProductDto userProductDto
                                = new UserProductDto(
                                tempProduct.getId(),
                                tempProduct.getName(),
                                tempProduct.getDescription(),
                                tempProduct.getPrice());
                        newProductList.add(userProductDto);
                    });
            return newProductList;
        } else {
            return null;
        }
    }

}
