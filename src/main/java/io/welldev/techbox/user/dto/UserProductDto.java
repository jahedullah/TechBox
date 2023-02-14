package io.welldev.techbox.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProductDto {
    private int id;
    private String name;
    private String vendor;
    private double price;
    private String imageUrl;

}
