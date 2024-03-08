package com.university.notesystem.domain.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class NoteEntryRequest {

    private Integer number;
    private Double value;

}
