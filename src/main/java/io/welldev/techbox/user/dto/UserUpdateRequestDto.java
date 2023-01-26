package io.welldev.techbox.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class UserUpdateRequestDto {
    @NotBlank
    @Size(min = 1, max = 20)
    private String firstName;
    @NotBlank
    @Size(min = 1, max = 20)
    private String lastName;
    @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email is not valid.")
    private String email;

    @Pattern(regexp = "^0[0-9]{10}$",
            message = "Number should be equal to 11 digits.")
    private String mobileNumber;
}
