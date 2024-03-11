package com.university.notesystem.domain.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectWithNotesModel {

    private int id;
    private String name;
    private List<SimpleNoteModel> notes;

}
