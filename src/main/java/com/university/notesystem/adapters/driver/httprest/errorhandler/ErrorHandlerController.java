package com.university.notesystem.adapters.driver.httprest.errorhandler;

import com.university.notesystem.adapters.driver.httprest.responses.ErrorResponse;
import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceAlreadyExistsException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ FieldException.class })
    public ResponseEntity<Object> handleFieldException(FieldException ex) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "FIELD")
                .message(ex.getMessage())
                .extra("field", ex.getField())
                .toEntity();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "FIELD")
                .message("¡Algunos valores se encuentran erroneos!")
                .extra("fields", errors)
                .toEntity();
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND")
                .message(ex.getMessage())
                .toEntity();
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .extra("resource", ex.getField())
                .toEntity();
    }

    @ExceptionHandler({ ResourceAlreadyExistsException.class })
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceAlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "RESOURCE_ALREADY_EXISTS")
                .message(ex.getMessage())
                .extra("field", ex.getField())
                .toEntity();
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .toEntity();
    }

}
