package com.university.notesystem.domain.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SimpleNoteModel {

    private Integer id;
    private Integer number;
    private Double note;

}
