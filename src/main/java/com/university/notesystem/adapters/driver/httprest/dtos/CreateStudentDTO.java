package com.university.notesystem.adapters.driver.httprest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CreateStudentDTO {

    @NotNull
    private int id;

    @NotNull
    private int code;

    @NotNull
    private String name;

}
