package com.university.notesystem.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectWithFinalNote {

    private int id;
    private String name;
    private double note;

}
