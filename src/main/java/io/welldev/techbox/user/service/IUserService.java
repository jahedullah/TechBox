package io.welldev.techbox.user.service;

import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.user.dto.*;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserDto getSingleUser(int userId);

    List<UserDto> getUserList();

    UserDto updateUser(int userId, UserUpdateRequestDto userUpdateRequestDto);

    UserPassChangeResponseDto updateUserPassword(int userId, UserPassChangeRequestDto userPassChangeRequestDto);

    UserDto deleteUserById(int userId);

    ProductDto productAddById(int userid, int productId);

    void productDeleteById(int userId, int productId);

    Set<UserProductDto> productList(int userId);

}
