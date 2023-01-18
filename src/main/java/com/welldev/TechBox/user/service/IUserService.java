package com.welldev.TechBox.user.service;

import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserProductDto;
import com.welldev.TechBox.user.dto.UserUpdateRequestDto;

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
