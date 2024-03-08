package com.university.notesystem.domain.exceptions;

import lombok.Getter;

@Getter
public class FieldException extends RuntimeException {

    private final String field;

    public FieldException(String field, String message) {
        super(message);
        this.field = field;
    }

}

