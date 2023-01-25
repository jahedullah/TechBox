package io.welldev.techbox.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRegisterResponseDto {
    private int id;
    private String name;
    private String vendor;
    private double price;
}
