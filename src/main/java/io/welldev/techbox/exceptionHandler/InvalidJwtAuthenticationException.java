package io.welldev.techbox.exceptionHandler;

public class InvalidJwtAuthenticationException extends RuntimeException{
    public InvalidJwtAuthenticationException(String message){
        super(message);
    }
}
