package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.StudentWithAllFinalNoteModel;

import java.util.List;

public interface StudentGetSubjectWithFinalNoteByAll {

    List<StudentWithAllFinalNoteModel> getAll();

}
