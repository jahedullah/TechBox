package com.welldev.TechBox.controllers;


import com.welldev.TechBox.model.dao.UserDao;
import com.welldev.TechBox.model.dto.Product.ProductDto;
import com.welldev.TechBox.model.dto.Product.ProductUpdateRequestDto;
import com.welldev.TechBox.model.dto.UserDto.UserDto;
import com.welldev.TechBox.model.dto.UserDto.UserUpdateRequestDto;
import com.welldev.TechBox.model.entity.Product;
import com.welldev.TechBox.model.service.UserService;
import com.welldev.TechBox.string.PRODUCT_URL;
import com.welldev.TechBox.string.USER_URL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserDao userDao;
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());

    }
    @GetMapping(USER_URL.USER_WITH_ID)
    public ResponseEntity<UserDto> getSingleUser(@PathVariable int userId) {
        Optional<UserDto> userDto = Optional.ofNullable(userService.getSingleUser(userId));
        return userDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = USER_URL.USER_UPDATE_BY_ID)
    public ResponseEntity<UserDto>
    updateUser(@Valid @PathVariable int userId,
               @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws NullPointerException {


        Optional<UserDto> userDto = Optional.ofNullable(userService.updateUser(userId, userUpdateRequestDto));
        return userDto.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }


    @GetMapping(USER_URL.USER_PRODUCTS_LIST)
    public ResponseEntity<List<Product>> productsList(HttpServletRequest request) {
        List<Product> productList = userDao.productsList(request);
        return ResponseEntity.ok(productList);
    }

    @PutMapping(USER_URL.USER_PRODUCTS_DELETE_BY_ID)
    public ResponseEntity<HttpStatus> productsDeleteById(@PathVariable int pid,
                                                         HttpServletRequest request) {
        try {
            userDao.productsDeleteById(pid, request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
