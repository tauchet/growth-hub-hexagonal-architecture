package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.dtos.StudentWithAllFinalNoteDTO;

import java.util.List;

public interface StudentGetSubjectWithFinalNoteByAll {

    List<StudentWithAllFinalNoteDTO> getAll();

}
