package io.welldev.techbox.product.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductRequestDto {

    @NotBlank
    @Size(min = 1, max = 30)
    private String name;


    @NotBlank
    @Size(min = 1, max = 30)
    private String vendor;


    @Range(min = 1, max = 1000000)
    private double price;

    @NotBlank
    private String imageUrl;

}
