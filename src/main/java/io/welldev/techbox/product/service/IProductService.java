package io.welldev.techbox.product.service;


import io.welldev.techbox.product.dto.*;

import java.util.List;


public interface IProductService {

    ProductDto getSingleProduct(int productId);

    List<ProductDto> getProductList();

    List<ProductDto> getProductsByVendor(String vendor);

    ProductRegisterResponseDto addProduct(ProductRegisterRequestDto productRegisterRequestDto);

    ProductDto updateProduct(int productId, ProductUpdateRequestDto productUpdateRequestDto);
    ProductDto patchProduct(int productId, ProductPatchRequestDto productPatchRequestDto);

    ProductDto deleteProduct(int productId);


}
