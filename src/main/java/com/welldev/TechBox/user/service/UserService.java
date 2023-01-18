package com.welldev.TechBox.user.service;

import com.welldev.TechBox.product.dao.IProductDao;
import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.entity.Product;
import com.welldev.TechBox.user.dao.IUserDao;
import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserProductDto;
import com.welldev.TechBox.user.dto.UserUpdateRequestDto;
import com.welldev.TechBox.user.entity.User;
import com.welldev.TechBox.userProductJoin.dao.IUserProductDao;
import com.welldev.TechBox.userProductJoin.entity.UserProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserProductDao userProductDao;

    private final IUserDao userDao;
    private final IProductDao productDao;

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
            boolean exist = userProductDao.isUserAndProductRowExist(userId, productId);
            Product product = productDao.getProduct(productId);
            User user = userDao.getUser(userId);
            if(exist){
                userProductDao.increaseUserProductRowQuantityByOne(userId, productId);
                productDao.decreaseProductCountByOne(product);
            }else {
                userDao.addProduct(user, product);
                productDao.addUser(product, user);
                userProductDao.setUserProductRowQuantityToOne(userId, productId);
                productDao.decreaseProductCountByOne(product);
            }
            UserProduct userProduct = userProductDao.getUserProductRow(userId, productId);
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    userProduct.getQuantity());
        }else {
            return null;
        }
    }

    @Override
    public UserProductDto productDeleteById(int userId, int productId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            boolean exist = userProductDao.isUserAndProductRowExist(userId, productId);
            Product product = productDao.getProduct(productId);
            User user = userDao.getUser(userId);
            if(exist){
                userProductDao.decreaseUserProductRowQuantityByOne(userId, productId);
                productDao.increaseProductCountByOne(product);
            }else {
                userDao.productDeleteFromUser(user, productId);
                productDao.increaseProductCountByOne(product);
            }
            UserProduct userProduct = userProductDao.getUserProductRow(userId, productId);
            return new UserProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    userProduct.getQuantity()

            );
        }else {
            return null;
        }
    }

    @Override
    public List<UserProductDto> productList(int userId) {
        if (Objects.equals(userDao.getUser(userId).getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            List<Product> productList = userDao.productList(userDao.getUser(userId));
            List<UserProductDto> newProductList = new ArrayList<>();
            productList.forEach(
                    (tempProduct) -> {
                        int quantity = userProductDao.getProductQuantityByUserIdAndProductId(userId,tempProduct.getId());
                        UserProductDto userProductDto
                                = new UserProductDto(
                                tempProduct.getId(),
                                tempProduct.getName(),
                                tempProduct.getDescription(),
                                tempProduct.getPrice(),
                                quantity
                        );
                        newProductList.add(userProductDto);
                    });
            return newProductList;
        } else {
            return null;
        }
    }

}
