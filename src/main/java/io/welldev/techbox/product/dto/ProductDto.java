package io.welldev.techbox.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private double price;

}
