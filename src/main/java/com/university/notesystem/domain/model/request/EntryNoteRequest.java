package com.university.notesystem.domain.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class EntryNoteRequest {

    private Integer number;
    private Double value;

}
