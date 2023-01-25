package io.welldev.techbox.user.controller;


import io.welldev.techbox.constant.USER_URL;
import io.welldev.techbox.exceptionHandler.UnauthorizedException;
import io.welldev.techbox.product.dto.ProductDto;

import io.welldev.techbox.exceptionHandler.dto.ErrorResponse;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import io.welldev.techbox.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class UserController {


    private final IUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUserList() {
        return ResponseEntity.ok(userService.getUserList());

    }

    @GetMapping(USER_URL.USER_WITH_ID)
    public ResponseEntity<UserDto> getSingleUser(@Valid @PathVariable int userId) {

        Optional<UserDto> userDto = Optional.ofNullable(userService.getSingleUser(userId));
        return userDto
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .notFound()
                                .build());
    }


    @PutMapping(value = USER_URL.USER_UPDATE_BY_ID)
    public ResponseEntity<UserDto>
    updateUser(@Valid @PathVariable int userId,
               @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) throws NullPointerException {
        Optional<UserDto> userDto = Optional.ofNullable(userService.updateUser(userId, userUpdateRequestDto));
        return userDto
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .build());

    }


    @GetMapping(value = USER_URL.USER_PRODUCT_LIST)
    public ResponseEntity<Set<UserProductDto>> userProductsList(@PathVariable int userId) {
        Optional<Set<UserProductDto>> productDtoList = Optional.ofNullable(userService.productList(userId));
        return productDtoList
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .build());
    }


    @DeleteMapping(value = USER_URL.USER_DELETE_BY_ID)
    public ResponseEntity<UserDto> deleteProduct(@PathVariable int userId) throws NullPointerException {
        Optional<UserDto> userDto = Optional.ofNullable(userService.deleteUser(userId));
        return userDto
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .build());
    }


    @PatchMapping(USER_URL.USER_PRODUCT_DELETE_BY_ID)
    public ResponseEntity<ErrorResponse> productDeleteById(@PathVariable int userId,
                                                           @PathVariable int productId) {
        try {
            userService.productDeleteById(userId, productId);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (UnauthorizedException e) {
            ErrorResponse error = new ErrorResponse();
            error.setMessage(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

    }


    @PatchMapping(value = USER_URL.USER_ADD_PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> addProduct(@PathVariable int productId,
                                                 @PathVariable int userId) {
        Optional<ProductDto> productDto = Optional.ofNullable(userService.addProduct(userId, productId));
        return productDto
                .map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity
                                .status(HttpStatus.UNAUTHORIZED)
                                .build());
    }


}
