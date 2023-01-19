package io.welldev.techbox.product.service;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductRegisterRequestDto;
import io.welldev.techbox.product.dto.ProductRegisterResponseDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
import io.welldev.techbox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final IProductDao productDao;


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
