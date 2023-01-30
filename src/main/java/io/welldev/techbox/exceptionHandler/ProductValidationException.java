package io.welldev.techbox.exceptionHandler;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class ProductValidationException extends RuntimeException {
    private final HashMap<String, String> errors;

    public ProductValidationException(HashMap<String, String> errors) {
        super();
        this.errors = errors;
    }
}
