package com.university.notesystem.domain.model;

import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class StudentWithAllFinalNoteModel {

    private int id;
    private String name;
    private List<SubjectWithFinalNoteModel> notes;

}
