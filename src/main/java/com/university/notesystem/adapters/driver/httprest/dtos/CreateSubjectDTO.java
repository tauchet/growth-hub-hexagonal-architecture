package com.university.notesystem.adapters.driver.httprest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class CreateSubjectDTO {

    @NotNull
    private Integer id;

    @NotNull
    private String name;

}
