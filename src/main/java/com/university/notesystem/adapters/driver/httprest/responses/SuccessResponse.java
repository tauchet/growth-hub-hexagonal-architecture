package com.university.notesystem.adapters.driver.httprest.responses;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class SuccessResponse<T> {

    private int status;
    private T success;

    public SuccessResponse(HttpStatusCode code, T success) {
        this.status = code.value();
        this.success = success;
    }

    public static <T> ResponseEntity<SuccessResponse<T>> create(HttpStatusCode code, T val) {
        return new ResponseEntity<>(
                new SuccessResponse<>(code, val),
                code
        );
    }

}
