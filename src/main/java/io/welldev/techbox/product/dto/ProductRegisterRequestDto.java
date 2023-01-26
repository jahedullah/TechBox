package io.welldev.techbox.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductRegisterRequestDto {

    @NotBlank
    @Size(min = 3, max = 20)
    private String name;


    @NotBlank
    @Size(min = 1, max = 40)
    private String vendor;


    @Range(min = 1, max = 200000)
    private double price;

}
