package com.university.notesystem.domain.exceptions;


import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {

    private final String field;

    public ResourceNotFoundException(String field, String message) {
        super(message);
        this.field = field;
    }

}
