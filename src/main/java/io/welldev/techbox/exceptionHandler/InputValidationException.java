package io.welldev.techbox.exceptionHandler;

public class InputValidationException extends RuntimeException{
    public InputValidationException(String message){
        super(message);
    }
}
