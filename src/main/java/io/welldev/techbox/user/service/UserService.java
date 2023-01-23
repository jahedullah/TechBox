package io.welldev.techbox.user.service;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.dao.IUserDao;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import io.welldev.techbox.user.entity.User;
import io.welldev.techbox.userProductJoin.dao.IUserProductDao;
import io.welldev.techbox.userProductJoin.entity.UserProduct;
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
            boolean exist = userProductDao.isUserAndProductRowExist(userId, productId);
            Product product = productDao.getProduct(productId);
            User user = userDao.getUser(userId);
            if (exist) {
                userProductDao.increaseUserProductRowQuantityByOne(userId, productId);
                productDao.decreaseProductCountByOne(product);
            } else {
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
        } else {
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
            if (exist) {
                if(userProductDao.getProductQuantityByUserIdAndProductId(userId, productId) > 1) {
                    userProductDao.decreaseUserProductRowQuantityByOne(userId, productId);
                    productDao.increaseProductCountByOne(product);
                }else {
                    userDao.productDeleteFromUser(user, productId);
                    productDao.increaseProductCountByOne(product);
                }
            }
            UserProduct userProduct = userProductDao.getUserProductRow(userId, productId);
            return new UserProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    userProduct.getQuantity()

            );
        } else {
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
                        int quantity = userProductDao.getProductQuantityByUserIdAndProductId(userId, tempProduct.getId());
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
