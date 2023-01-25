package io.welldev.techbox.product.entity;

import io.welldev.techbox.user.entity.User;
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

    private String vendor;

    private double price;
    @ManyToMany(mappedBy = "productList", fetch = FetchType.EAGER)
    private List<User> userList;

    public Product(String name, String description, double price) {
        this.name = name;
        this.vendor = description;
        this.price = price;
    }

}
