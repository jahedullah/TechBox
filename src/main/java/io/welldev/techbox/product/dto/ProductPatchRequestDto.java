package io.welldev.techbox.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_ABSENT)
@Getter @Setter
public class ProductPatchRequestDto {

    private String name;

    private String vendor;

    private double price;

}
