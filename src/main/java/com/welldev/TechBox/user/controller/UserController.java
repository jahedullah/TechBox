package com.welldev.TechBox.user.controller;


import com.welldev.TechBox.constant.USER_URL;
import com.welldev.TechBox.product.dto.ProductDto;

import com.welldev.TechBox.user.dto.UserDto;
import com.welldev.TechBox.user.dto.UserProductDto;
import com.welldev.TechBox.user.dto.UserUpdateRequestDto;
import com.welldev.TechBox.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());

    }
    @GetMapping(USER_URL.USER_WITH_ID)
    public ResponseEntity<UserDto> getSingleUser(@Valid @PathVariable int userId){

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



    @GetMapping(value = USER_URL.USER_PRODUCT_LIST)
    public ResponseEntity<List<UserProductDto>> userProductsList(@PathVariable int userId){
        Optional<List<UserProductDto>> productDtoList = Optional.ofNullable(userService.productList(userId));
        return productDtoList.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }



    @DeleteMapping(value = USER_URL.USER_DELETE_BY_ID)
    public ResponseEntity<UserDto> deleteProduct(@PathVariable int userId) throws NullPointerException {
        Optional<UserDto> userDto = Optional.ofNullable(userService.deleteUser(userId));
        return userDto.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }


    @DeleteMapping (USER_URL.USER_PRODUCT_DELETE_BY_ID)
    public ResponseEntity<UserProductDto> productDeleteById(@PathVariable int userId,
                                                            @PathVariable int productId){
        Optional<UserProductDto> userProductDto = Optional.ofNullable(userService.productDeleteById(userId, productId));
        return userProductDto.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

    }



    @PutMapping(value = USER_URL.USER_ADD_PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> addProduct(@PathVariable int productId,
                                                 @PathVariable int userId){
        Optional<ProductDto> productDto = Optional.ofNullable(userService.addProduct(userId,productId));
        return productDto.map(ResponseEntity::ok).orElseGet(() ->
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }



}
