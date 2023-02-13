package io.welldev.techbox.exceptionHandler;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import io.welldev.techbox.exceptionHandler.dto.ErrorResponse;
import org.hibernate.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


import java.sql.DataTruncation;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgsNotValidException(
            MethodArgumentNotValidException ex) {
        Map<String, String> response = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            response.put(fieldName, message);
        });
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleSQLViolationException(
            SQLIntegrityConstraintViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("Unique Name Required", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }


    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleSQLViolationException(
            NullPointerException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage("Resource id is not found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<ErrorResponse> handleNumberFormatException(
            NumberFormatException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage("Input format mismatch");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }


    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(UnauthorizedException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignatureException(SignatureException e) {
        Map<String, String> response = new HashMap<>();
        response.put("Error :", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(IllegalArgumentException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorResponse handleResourceNotFound(ExpiredJwtException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return error;
    }

    @ExceptionHandler(UserExistException.class)
    public ResponseEntity<ErrorResponse> handleUserExist(UserExistException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataTruncation.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(DataTruncation e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<ErrorResponse> handleInputValidation(InputValidationException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductValidationException.class)
    public ResponseEntity<Object> handleProductValidation(ProductValidationException e) {
        return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleConflict(RuntimeException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage("Bad request");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleObjectNotFound(ObjectNotFoundException e) {
        ErrorResponse error = new ErrorResponse();
        error.setMessage("Resource not found.");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
