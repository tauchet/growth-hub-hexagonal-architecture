package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.SubjectWithFinalNote;

import java.util.List;

public interface StudentGetSubjectWithFinalNoteById {

    List<SubjectWithFinalNote> getAllByIdOrCode(Integer id, Integer code);

}
