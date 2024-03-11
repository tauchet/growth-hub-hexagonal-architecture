package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;

import java.util.List;

public interface StudentGetSubjectWithFinalNoteById {

    List<SubjectWithFinalNoteModel> getAllByIdOrCode(Integer id, Integer code);

}
