package com.university.notesystem.adapters.driver.httprest.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CreateStudentDTO {

    private int id;
    private int code;
    private String name;

}
