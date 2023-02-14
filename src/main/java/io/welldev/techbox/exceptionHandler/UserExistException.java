package io.welldev.techbox.exceptionHandler;

public class UserExistException extends RuntimeException{

    public UserExistException(String message){
        super(message);
    }
}
