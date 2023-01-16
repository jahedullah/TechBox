package com.welldev.TechBox.model.service.impl;

import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.entity.User;
import com.welldev.TechBox.model.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    @Override
    public UserDto getSingleUser(int userId) {
        Optional<User> user = Optional.ofNullable(userDao.getUser(userId));
        if (user.isPresent()) {
            return new UserDto(
                    user.get().getId(),
                    user.get().getFirstname(),
                    user.get().getLastname(),
                    user.get().getEmail(),
                    user.get().getMobilenumber(),
                    user.get().getUsertype());
        }else {
            return null;
        }
    }

    @Override
    public List<UserDto> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public UserDto updateUser(int productId, UserUpdateRequestDto userUpdateRequestDto) {
        return null;
    }

    @Override
    public ProductDto deleteProduct(int user) {
        return null;
    }
}
