package com.welldev.TechBox.product.dao;


import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.dto.ProductUpdateRequestDto;
import com.welldev.TechBox.product.entity.Product;
import com.welldev.TechBox.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public interface IProductDao {

    Product createProduct(Product productToCreate);

    List<ProductDto> getProducts();

    Product deleteProduct(int pid);

    Product getProduct(int productId);

    Product updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);

    void increaseProductCountByOne(Product product);
    void decreaseProductCountByOne(Product product);

    void addUser(Product product, User user);
    ArrayList<String> findAllProductName();





}
