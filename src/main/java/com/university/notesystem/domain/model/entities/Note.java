package com.university.notesystem.domain.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class Note {

    private Integer id;
    private SubjectStudent subjectStudent;
    private Integer number;
    private Double note;

}
