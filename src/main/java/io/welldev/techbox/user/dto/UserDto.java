package io.welldev.techbox.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String userType;
}
