package io.welldev.techbox.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
public class ProductPatchRequestDto {


    @Size(max = 30)
    private String name;


    @Size(max = 30)
    private String vendor;


    @Range(max = 1000000)
    private double price;

    @Size(max = 100)
    private String imageUrl;

}
