package com.university.notesystem.adapters.driver.httprest.errorhandler;

import com.university.notesystem.adapters.driver.httprest.responses.ErrorResponse;
import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ FieldException.class })
    public ResponseEntity<ErrorResponse> handle(FieldException ex) {
        return new ErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY, "FIELD")
                .message(ex.getMessage())
                .extra("field", ex.getField())
                .toEntity();
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<ErrorResponse> handle(EntityNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "NOT_FOUND")
                .message(ex.getMessage())
                .toEntity();
    }

    @ExceptionHandler({ ResourceNotFoundException.class })
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException ex) {
        return new ErrorResponse(HttpStatus.NOT_FOUND, "RESOURCE_NOT_FOUND")
                .message(ex.getMessage())
                .extra("resource", ex.getField())
                .toEntity();
    }

    @ExceptionHandler({ RuntimeException.class })
    public ResponseEntity<ErrorResponse> handle(RuntimeException ex) {
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR")
                .message(ex.getMessage())
                .toEntity();
    }

}
