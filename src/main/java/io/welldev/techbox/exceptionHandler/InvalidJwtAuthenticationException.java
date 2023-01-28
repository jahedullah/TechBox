package io.welldev.techbox.exceptionHandler;

import lombok.Getter;

import javax.security.sasl.AuthenticationException;

@Getter
public class InvalidJwtAuthenticationException extends AuthenticationException {

    public InvalidJwtAuthenticationException(String message){
        super(message);
    }
}
