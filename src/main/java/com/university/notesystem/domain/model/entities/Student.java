package com.university.notesystem.domain.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Student {

    private int id;
    private int code;
    private String name;

}
