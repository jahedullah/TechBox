package io.welldev.techbox.product.dao;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductRequestDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface IProductDao {

    Product createProduct(Product productToCreate);

    List<ProductDto> getProducts();
    List<ProductDto> getProductsByVendor(String vendor);
    Product deleteProduct(int productId);

    Product getProduct(int productId);

    Product updateProduct(Product product);
    void patchProduct(Product product);

    void addUser(Product product, User user);

}
