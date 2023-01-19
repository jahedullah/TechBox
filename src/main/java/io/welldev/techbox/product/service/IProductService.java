package io.welldev.techbox.product.service;


import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductRegisterRequestDto;
import io.welldev.techbox.product.dto.ProductRegisterResponseDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;

import java.util.List;


public interface IProductService {

    ProductDto getSingleProduct(int productId);

    List<ProductDto> getProductList();

    ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto);

    ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);

    ProductDto deleteProduct(int productId);
}
