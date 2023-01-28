package io.welldev.techbox.product.service;


import io.welldev.techbox.product.dto.*;

import java.util.List;


public interface IProductService {

    ProductDto getSingleProduct(int productId);

    List<ProductDto> getProductList();

    List<ProductDto> getProductsByVendor(String vendor);

    ProductRegisterResponseDto addProduct(ProductRequestDto productRegisterRequestDto);

    ProductDto updateProduct(int productId, ProductRequestDto productUpdateRequestDto);
    ProductDto patchProduct(int productId, ProductPatchRequestDto productPatchRequestDto);

    ProductDto deleteProduct(int productId);


}
