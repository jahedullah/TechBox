package io.welldev.techbox.user.dto;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class UserPassChangeRequestDto {
    @Size(min = 1, max = 30)
    String oldPassWord;

    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$",
            message = "At least one upper case English letter, " +
                    "At least one lower case English letter, " +
                    "At least one digit, At least one special character," +
                    "Minimum eight in length.")

    @Size(max = 30, message = "new password size should be less then 30.")
    String newPassWord;

    @Size(min = 1, max = 30)
    String confirmPassWord;
}
