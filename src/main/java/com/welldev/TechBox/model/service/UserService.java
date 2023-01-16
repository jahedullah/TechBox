package com.welldev.TechBox.model.service;

import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;

import java.util.List;

public interface UserService {
    UserDto getSingleUser(int userId);
    List<UserDto> getUserList();

    UserDto updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto);
    UserDto deleteUser(int userId);
}
