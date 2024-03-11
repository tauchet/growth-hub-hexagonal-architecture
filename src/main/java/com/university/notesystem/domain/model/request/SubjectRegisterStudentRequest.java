package com.university.notesystem.domain.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SubjectRegisterStudentRequest {

    private int studentId;
    private int subjectId;
    private List<EntryNoteRequest> notes;

}
