package io.welldev.techbox.product.entity;

import io.welldev.techbox.user.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private int id;

    private String name;

    private String vendor;

    private double price;

    private String imageUrl;
    @ManyToMany(mappedBy = "productList", fetch = FetchType.LAZY)
    private Set<User> userList;

    public Product(String name, String vendor, double price, String imageUrl) {
        this.name = name;
        this.vendor = vendor;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
