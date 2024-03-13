package com.university.notesystem.adapters.driver.httprest.dtos;


import com.university.notesystem.domain.model.request.EntryNoteRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class SubjectUpdateStudentDTO {

    @NotNull
    private Integer studentId;

    @NotNull
    @NotEmpty
    private List<EntryNoteRequest> notes;

}
