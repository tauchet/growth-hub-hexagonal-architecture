package com.university.notesystem.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectWithFinalNoteModel {

    private int id;
    private String name;
    private double note;

}
