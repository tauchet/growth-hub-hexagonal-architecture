package com.university.notesystem.adapters.driver.httprest.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private Map<String, Object> extra;

    public ErrorResponse(HttpStatusCode code, String error) {
        this.status = code.value();
        this.error = error;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse extra(String key, Object value) {
        if (this.extra == null) {
            this.extra = new HashMap<>();
        }
        this.extra.put(key, value);
        return this;
    }

    public ResponseEntity<Object> toEntity() {
        return new ResponseEntity<>(this, HttpStatusCode.valueOf(this.status));
    }

}
