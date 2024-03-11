package com.university.notesystem.domain.model.dtos;

import com.university.notesystem.domain.model.SubjectWithFinalNote;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class StudentWithAllFinalNoteDTO {

    private int id;
    private String name;
    private List<SubjectWithFinalNote> notes;

}
