package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.dtos.SubjectWithFinalNoteDTO;

import java.util.List;

public interface StudentGetSubjectWithFinalNoteById {

    List<SubjectWithFinalNoteDTO> getAllByIdOrCode(Integer id, Integer code);

}
