package io.welldev.techbox.product.dao;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
import io.welldev.techbox.product.entity.Product;
import io.welldev.techbox.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface IProductDao {

    Product createProduct(Product productToCreate);

    List<ProductDto> getProducts();
    List<ProductDto> getProductsByVendor(String vendor);
    Product deleteProduct(int productId);

    Product getProduct(int productId);

    Product updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);

    void addUser(Product product, User user);

}
