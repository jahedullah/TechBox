package io.welldev.techbox.user.service;

import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;

import java.util.List;

public interface IUserService {
    UserDto getSingleUser(int userId);

    List<UserDto> getUserList();

    UserDto updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto);

    UserDto deleteUser(int userId);

    ProductDto addProduct(int userid, int productId);

    UserProductDto productDeleteById(int userId, int productId);

    List<UserProductDto> productList(int userId);

}
