package io.welldev.techbox.product.dao;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
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
