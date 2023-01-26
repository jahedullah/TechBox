package io.welldev.techbox.product.service;

import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductRegisterRequestDto;
import io.welldev.techbox.product.dto.ProductRegisterResponseDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
import io.welldev.techbox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {


    private final IProductDao productDao;


    @Override
    @Transactional
    public ProductDto getSingleProduct(int productId) {
        Optional<Product> product = Optional.ofNullable(productDao.getProduct(productId));
        return product.map(value -> new ProductDto(
                value.getId(),
                value.getName(),
                value.getVendor(),
                value.getPrice())).orElse(null);
    }

    @Override
    @Transactional
    public List<ProductDto> getProductList() {
        return productDao.getProducts();
    }

    @Override
    @Transactional
    public List<ProductDto> getProductsByVendor(String vendor) {
        return productDao.getProductsByVendor(vendor);
    }

    @Transactional
    @Override
    public ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto) {
        Product productToCreate = new Product(
                productRegisterRequestDto.getName(),
                productRegisterRequestDto.getVendor(),
                productRegisterRequestDto.getPrice());
        Product product = productDao.createProduct(productToCreate);
        return new ProductRegisterResponseDto(
                product.getId(),
                product.getName(),
                product.getVendor(),
                product.getPrice()
        );
    }


    @Override
    @Transactional
    public ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> productToUpdate = Optional.ofNullable(productDao.updateProduct(productId, productUpdateRequestDto));
        return productToUpdate.map(value -> new ProductDto(
                value.getId(),
                value.getName(),
                value.getVendor(),
                value.getPrice())).orElse(null);
    }


    @Override
    @Transactional
    public ProductDto deleteProduct(int productId) {
        Product productToDelete = productDao.deleteProduct(productId);
        return new ProductDto(
                productToDelete.getId(),
                productToDelete.getName(),
                productToDelete.getVendor(),
                productToDelete.getPrice());
    }

}
