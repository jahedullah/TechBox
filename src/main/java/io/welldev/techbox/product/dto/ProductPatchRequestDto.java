package io.welldev.techbox.product.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductPatchRequestDto {

    private String name;

    private String vendor;

    private double price;

}
