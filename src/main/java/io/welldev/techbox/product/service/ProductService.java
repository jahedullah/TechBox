package io.welldev.techbox.product.service;


import io.welldev.techbox.constant.MESSAGE;
import io.welldev.techbox.exceptionHandler.ProductValidationException;
import io.welldev.techbox.exceptionHandler.ResourceNotFoundException;
import io.welldev.techbox.product.dao.IProductDao;
import io.welldev.techbox.product.dto.*;
import io.welldev.techbox.product.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
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
                value.getPrice(),
                value.getImageUrl())).orElse(null);
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
    public ProductRegisterResponseDto addProduct(ProductRequestDto productRegisterRequestDto) {
        Product productToCreate = new Product(
                productRegisterRequestDto.getName(),
                productRegisterRequestDto.getVendor(),
                productRegisterRequestDto.getPrice(),
                productRegisterRequestDto.getImageUrl());
        Product product = productDao.createProduct(productToCreate);
        return new ProductRegisterResponseDto(
                product.getId(),
                product.getName(),
                product.getVendor(),
                product.getPrice(),
                product.getImageUrl()
        );
    }


    @Override
    @Transactional
    public ProductDto updateProduct(int productId, ProductRequestDto productUpdateRequestDto) {
        Optional<Product> productToUpdate = Optional.ofNullable(productDao.getProduct(productId));
        if(!productToUpdate.isPresent()){
            throw new ResourceNotFoundException(MESSAGE.PRODUCT_NOT_FOUND);
        }
        Product product = productToUpdate.get();
        product.setName(productUpdateRequestDto.getName());
        product.setVendor(productUpdateRequestDto.getVendor());
        product.setPrice(productUpdateRequestDto.getPrice());
        productDao.updateProduct(product);
        return new ProductDto(product.getId(),
                product.getName(),
                product.getVendor(),
                product.getPrice(),
                product.getImageUrl());
    }

//    @Override
//    @Transactional
//    public ProductDto patchProduct(int productId, ProductPatchRequestDto productPatchRequestDto) {
//        HashMap<String, String> errors = new HashMap<>();
//        Optional<Product> productToUpdate = Optional.ofNullable(productDao.getProduct(productId));
//        if (!productToUpdate.isPresent()) {
//            return null;
//        }
//        Product product = productToUpdate.get();
//
//        if (productPatchRequestDto.getName() != null) {
//            int length = productPatchRequestDto.getName().length();
//            if (length == 0 || length > 40) {
//                errors.put("name", "product name length must be within 1 and 40 inclusive.");
//            } else {
//                product.setName(productPatchRequestDto.getName());
//            }
//        }
//
//        if (productPatchRequestDto.getVendor() != null) {
//            int length = productPatchRequestDto.getVendor().length();
//            if (length == 0 || length > 20) {
//                errors.put("vendor", "vendor name length must be within 1 and 20 inclusive.");
//            } else {
//                product.setVendor(productPatchRequestDto.getVendor());
//            }
//        }
//
//        if (productPatchRequestDto.getPrice() != 0.0) {
//            double price = productPatchRequestDto.getPrice();
//            if (price <= 0 || price >= 1000001) {
//                errors.put("price", "price limit exceeded.");
//            } else {
//                product.setPrice(productPatchRequestDto.getPrice());
//            }
//        }
//
//        if (errors.isEmpty()) {
//            productDao.patchProduct(product);
//            return new ProductDto(product.getId(),
//                    product.getName(),
//                    product.getVendor(),
//                    product.getPrice(),
//                    product.getImageUrl());
//        } else {
//            throw new ProductValidationException(errors);
//        }
//    }


    @Override
    @Transactional
    public ProductDto patchProduct(int productId, ProductPatchRequestDto productPatchRequestDto) {
        HashMap<String, String> errors = new HashMap<>();
        Optional<Product> productToUpdate = Optional.ofNullable(productDao.getProduct(productId));
        if (!productToUpdate.isPresent()) {
            return null;
        }
        Product product = productToUpdate.get();

        String name = productPatchRequestDto.getName();
        if (name != null && !name.trim().isEmpty()) {
            int length = name.length();
            if(length < 1 || length > 40) {
                errors.put("name", "product name length must be within 3 and 40 inclusive.");
            } else {
                product.setName(name);
            }
        }


        String vendor = productPatchRequestDto.getVendor();
        if (vendor != null && !vendor.trim().isEmpty()) {
            int length = vendor.length();
            if (length < 1 || length > 30) {
                errors.put("vendor", "vendor name length must be within 1 and 30 inclusive.");
            } else {
                product.setVendor(vendor);
            }
        }


        double price = productPatchRequestDto.getPrice();
        if (price != 0.0) {
            if (price < 0 || price > 1000000) {
                errors.put("price", "price must be between 0 and 1,000,000 inclusive.");
            } else {
                product.setPrice(price);
            }
        }


        String imageUrl = productPatchRequestDto.getImageUrl();
        if (imageUrl != null && !imageUrl.trim().isEmpty()) {
            int length = imageUrl.length();
            if (length < 1 || length > 150) {
                errors.put("imageUrl", "image url length must be within 1 and 150 inclusive.");
            } else {
                product.setImageUrl(imageUrl);
            }
        }

        if (errors.isEmpty()) {
            productDao.patchProduct(product);
            return new ProductDto(product.getId(),
                    product.getName(),
                    product.getVendor(),
                    product.getPrice(),
                    product.getImageUrl());
        } else {
            throw new ProductValidationException(errors);
        }
    }














    @Override
    @Transactional
    public ProductDto deleteProduct(int productId) {
        Optional<Product> product = Optional.ofNullable(productDao.getProduct(productId));
        if(!product.isPresent()) {
            throw new ResourceNotFoundException(MESSAGE.PRODUCT_NOT_FOUND);
        }
        Product productToDelete = productDao.deleteProduct(productId);
        return new ProductDto(
                productToDelete.getId(),
                productToDelete.getName(),
                productToDelete.getVendor(),
                productToDelete.getPrice(),
                productToDelete.getImageUrl());
    }

}
