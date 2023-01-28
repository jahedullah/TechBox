package io.welldev.techbox.user.service;

import io.welldev.techbox.exceptionHandler.ResourceNotFoundException;
import io.welldev.techbox.exceptionHandler.UnauthorizedException;
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

import javax.transaction.Transactional;
import java.util.*;

import static io.welldev.techbox.constant.MESSAGE.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserDao userDao;
    private final IProductDao productDao;

    @Override
    @Transactional
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
    @Transactional
    public List<UserDto> getUserList() {
        return userDao.getUserList();
    }


    @Override
    @Transactional
    public UserDto deleteUserById(int userId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            return null;
        }
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
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }


    @Override
    @Transactional
    public UserDto updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto) {
        User user = userDao.getUser(userId);
        if (user == null) {
            return null;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (Objects.equals(user.getEmail(), authentication.getName())) {
            User userToUpdate = userDao.updateUser(userId, userUpdateRequestDto);
            return new UserDto(
                    userToUpdate.getId(),
                    userToUpdate.getFirstName(),
                    userToUpdate.getLastName(),
                    userToUpdate.getEmail(),
                    userToUpdate.getMobileNumber(),
                    userToUpdate.getUserType());
        } else {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }


    @Override
    @Transactional
    public ProductDto productAddById(int userId, int productId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
        if (Objects.equals(user.getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            Product product = productDao.getProduct(productId);
            if (product == null) {
                throw new ResourceNotFoundException(PRODUCT_NOT_FOUND);
            }
            userDao.addProduct(user, product);
            productDao.addUser(product, user);
            return new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getVendor(),
                    product.getPrice());
        } else {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }


    @Override
    @Transactional
    public void productDeleteById(int userId, int productId) {
        User user = userDao.getUser(userId);
        if (user == null) {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
        if (Objects.equals(user.getEmail(),
                SecurityContextHolder.getContext().getAuthentication().getName())) {
            Set<Product> userProducts = user.getProductList();
            boolean isProductExist = false;
            for (Product product : userProducts) {
                if (product.getId() == productId) {
                    isProductExist = true;
                    break;
                }
            }
            if (!isProductExist) {
                throw new ResourceNotFoundException(USERS_PRODUCT_NOT_FOUND);
            }
            userDao.productDeleteFromUser(user, productId);
        } else {
            throw new UnauthorizedException(UNAUTHORIZED);
        }
    }


    @Override
    @Transactional
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
                                tempProduct.getVendor(),
                                tempProduct.getPrice());
                        newProductList.add(userProductDto);
                    });
            return newProductList;
        } else {
            return null;
        }
    }

}
