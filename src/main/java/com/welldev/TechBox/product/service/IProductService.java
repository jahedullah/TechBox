package com.welldev.TechBox.product.service;


import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.dto.ProductRegisterRequestDto;
import com.welldev.TechBox.product.dto.ProductRegisterResponseDto;
import com.welldev.TechBox.product.dto.ProductUpdateRequestDto;

import java.util.List;


public interface IProductService {

    ProductDto getSingleProduct(int productId);
    List<ProductDto> getProductList();

    ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto);

    ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);
    ProductDto deleteProduct(int productId);
}
