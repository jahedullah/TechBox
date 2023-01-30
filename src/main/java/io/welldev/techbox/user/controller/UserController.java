package io.welldev.techbox.user.controller;


import io.welldev.techbox.constant.USER_URL;
import io.welldev.techbox.exceptionHandler.ResourceNotFoundException;
import io.welldev.techbox.product.dto.ProductDto;

import io.welldev.techbox.exceptionHandler.dto.ErrorResponse;
import io.welldev.techbox.user.dto.UserDto;
import io.welldev.techbox.user.dto.UserProductDto;
import io.welldev.techbox.user.dto.UserUpdateRequestDto;
import io.welldev.techbox.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static io.welldev.techbox.constant.MESSAGE.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.*;

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
        return userDto.map(ResponseEntity::ok).
                orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }


    @PutMapping(value = USER_URL.USER_UPDATE_BY_ID)
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable int userId,
                                              @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        Optional<UserDto> userDto = Optional.ofNullable(userService.updateUser(userId, userUpdateRequestDto));
        return userDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }


    @GetMapping(value = USER_URL.USER_PRODUCT_LIST)
    public ResponseEntity<Set<UserProductDto>> userProductsList(@PathVariable int userId) {
        Optional<Set<UserProductDto>> productDtoList = Optional.ofNullable(userService.productList(userId));
        return productDtoList.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(UNAUTHORIZED).build());
    }


    @DeleteMapping(value = USER_URL.USER_DELETE_BY_ID)
    public ResponseEntity<UserDto> deleteUserById(@PathVariable int userId) {
        Optional<UserDto> userDto = Optional.ofNullable(userService.deleteUserById(userId));
        return userDto.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
    }


    @DeleteMapping(USER_URL.USER_PRODUCT_DELETE_BY_ID)
    public ResponseEntity<ErrorResponse> productDeleteById(@PathVariable int userId,
                                                           @PathVariable int productId) {
        userService.productDeleteById(userId, productId);
        return new ResponseEntity<>(ACCEPTED);
    }


    @PatchMapping(value = USER_URL.USER_ADD_PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> addProduct(@PathVariable int productId, @PathVariable int userId) {
        ProductDto productDto = userService.productAddById(userId, productId);
        return new ResponseEntity<>(productDto, OK);
    }
}
