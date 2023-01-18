package com.welldev.TechBox.product.controller;

import com.welldev.TechBox.product.service.ProductService;
import com.welldev.TechBox.constant.PRODUCT_URL;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.welldev.TechBox.product.dto.ProductDto;
import com.welldev.TechBox.product.dto.ProductRegisterResponseDto;
import com.welldev.TechBox.product.dto.ProductUpdateRequestDto;
import com.welldev.TechBox.product.dto.ProductRegisterRequestDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping(PRODUCT_URL.PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable int productId) {
        Optional<ProductDto> productDto = Optional.ofNullable(productService.getSingleProduct(productId));
        return productDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductList() {
        return ResponseEntity.ok(productService.getProductList());

    }


    @PostMapping()
    public ResponseEntity<ProductRegisterResponseDto>
    addProduct(@Valid @RequestBody
               ProductRegisterRequestDto productRegisterRequestDto) {
            return ResponseEntity.ok(productService.addProduct(productRegisterRequestDto));

    }


    @PutMapping(value = PRODUCT_URL.PRODUCT_UPDATE_BY_ID)
    public ResponseEntity<ProductDto>
    updateProduct(@Valid @PathVariable int productId,
                  @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) throws NullPointerException {

            ProductDto productDto =
                    productService.updateProduct(productId, productUpdateRequestDto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto);

    }


    @DeleteMapping(value = PRODUCT_URL.PRODUCT_DELETE_BY_ID)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) throws NullPointerException {
        try {
            ProductDto productDto =
                    productService.deleteProduct(productId);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}

