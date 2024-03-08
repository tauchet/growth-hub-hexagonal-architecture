package com.university.notesystem.domain.model.dtos;

import com.university.notesystem.domain.model.entities.Subject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectWithFinalNoteDTO {

    private int id;
    private String name;
    private double note;

}
