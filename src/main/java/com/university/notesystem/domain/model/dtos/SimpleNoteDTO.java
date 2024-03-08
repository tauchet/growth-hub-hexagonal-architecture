package com.university.notesystem.domain.model.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SimpleNoteDTO {

    private Integer id;
    private Integer number;
    private Double note;

}
