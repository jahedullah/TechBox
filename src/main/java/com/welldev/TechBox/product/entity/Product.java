package com.welldev.TechBox.product.entity;

import com.welldev.TechBox.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;
    private String name;

    private String description;

    private double price;
    private int productCount;
    @ManyToMany(mappedBy = "productList", fetch = FetchType.EAGER)
    private List<User> userList;

    public Product(String name, String description, double price, int productCount) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.productCount = productCount;
    }

}
