package com.welldev.TechBox.model.dto.UserDto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserProductDto {
    private int id;
    private String name;
    private String description;
    private double price;

}
