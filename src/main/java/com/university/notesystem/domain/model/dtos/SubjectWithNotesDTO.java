package com.university.notesystem.domain.model.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectWithNotesDTO {

    private int id;
    private String name;
    private List<SimpleNoteDTO> notes;

}
