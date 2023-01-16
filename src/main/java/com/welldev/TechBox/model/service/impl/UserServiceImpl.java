package com.welldev.TechBox.model.service.impl;

import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;
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
        if(Objects.equals(user.getEmail(), authentication.getName())) {
            User userToUpdate = userDao.updateUser(userId, userUpdateRequestDto);
            return new UserDto(
                    userToUpdate.getId(),
                    userToUpdate.getFirstname(),
                    userToUpdate.getLastname(),
                    userToUpdate.getEmail(),
                    userToUpdate.getMobilenumber(),
                    userToUpdate.getUsertype()
            );
        }else {
            return null;
        }
    }

    @Override
    public ProductDto deleteProduct(int user) {
        return null;
    }
}
