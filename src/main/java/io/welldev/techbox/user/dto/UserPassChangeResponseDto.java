package io.welldev.techbox.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor @Getter
public class UserPassChangeResponseDto {
    String oldPassWord;
    String newPassWord;
}
