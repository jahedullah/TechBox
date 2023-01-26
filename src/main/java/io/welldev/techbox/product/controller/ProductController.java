package io.welldev.techbox.product.controller;

import io.welldev.techbox.exceptionHandler.ResourceNotFoundException;
import io.welldev.techbox.product.service.IProductService;
import io.welldev.techbox.constant.PRODUCT_URL;
import io.welldev.techbox.product.dto.ProductDto;
import io.welldev.techbox.product.dto.ProductRegisterRequestDto;
import io.welldev.techbox.product.dto.ProductRegisterResponseDto;
import io.welldev.techbox.product.dto.ProductUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static io.welldev.techbox.constant.MESSAGE.PRODUCT_NOT_FOUND;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping(PRODUCT_URL.PRODUCT_WITH_ID)
    public ResponseEntity<ProductDto> getSingleProduct(@PathVariable int productId) {
        Optional<ProductDto> productDto = Optional.ofNullable(productService.getSingleProduct(productId));
        return productDto.map(ResponseEntity::ok).
                orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }


    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProductList(@RequestParam(value = "vendor", required = false) String vendor) {
        if (vendor != null) {
            return ResponseEntity.ok(productService.getProductsByVendor(vendor));
        }
        return ResponseEntity.ok(productService.getProductList());
    }


    @PostMapping()
    public ResponseEntity<ProductRegisterResponseDto>
    addProduct(@Valid @RequestBody
               ProductRegisterRequestDto productRegisterRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(productRegisterRequestDto));

    }


    @PutMapping(value = PRODUCT_URL.PRODUCT_UPDATE_BY_ID)
    public ResponseEntity<ProductDto>
    updateProduct(@Valid @PathVariable int productId,
                  @Valid @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        return Optional.ofNullable(productService.updateProduct(productId, productUpdateRequestDto))
                .map(productDto -> ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto))
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }


    @DeleteMapping(value = PRODUCT_URL.PRODUCT_DELETE_BY_ID)
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable int productId) {
        return Optional.ofNullable(productService.deleteProduct(productId))
                .map(productDto -> ResponseEntity.status(HttpStatus.ACCEPTED).body(productDto))
                .orElseThrow(() -> new ResourceNotFoundException(PRODUCT_NOT_FOUND));
    }

}

