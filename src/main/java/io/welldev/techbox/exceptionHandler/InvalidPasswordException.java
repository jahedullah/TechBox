package io.welldev.techbox.exceptionHandler;

public class InvalidPasswordException extends RuntimeException{

    public InvalidPasswordException(String message) {
        super(message);
    }
}
