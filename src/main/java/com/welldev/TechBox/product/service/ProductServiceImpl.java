package com.welldev.TechBox.product.service;

import com.welldev.TechBox.product.dao.ProductDao;
import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.dto.ProductRegisterRequestDto;
import com.welldev.TechBox.product.dto.ProductRegisterResponseDto;
import com.welldev.TechBox.product.dto.ProductUpdateRequestDto;
import com.welldev.TechBox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    private final ProductDao productDao;


    @Override
    public ProductDto getSingleProduct(int productId) {
        Optional<Product> product = Optional.ofNullable(productDao.getProduct(productId));
        if (product.isPresent()) {
            return new ProductDto(
                    product.get().getId(),
                    product.get().getName(),
                    product.get().getDescription(),
                    product.get().getPrice(),
                    product.get().getProductCount());
        }else {
            return null;
        }
    }

    @Override
    public List<ProductDto> getProductList() {
        return productDao.getProducts();
    }


    @Override
    public ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto) {
            Product productToCreate = new Product(
                    productRegisterRequestDto.getName(),
                    productRegisterRequestDto.getDescription(),
                    productRegisterRequestDto.getPrice(),
                    productRegisterRequestDto.getProductCount());

            Product product = productDao.createProduct(productToCreate);
            return new ProductRegisterResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getProductCount()
            );
    }


    @Override
    public ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Product productToUpdate = productDao.updateProduct(productId,productUpdateRequestDto);
        return new ProductDto(
                productToUpdate.getId(),
                productToUpdate.getName(),
                productToUpdate.getDescription(),
                productToUpdate.getPrice(),
                productToUpdate.getProductCount()
        );
    }


    @Override
    public ProductDto deleteProduct(int productId) {
        Product productToDelete = productDao.deleteProduct(productId);
        return new ProductDto(
                productToDelete.getId(),
                productToDelete.getName(),
                productToDelete.getDescription(),
                productToDelete.getPrice(),
                productToDelete.getProductCount()
        );
    }


}
