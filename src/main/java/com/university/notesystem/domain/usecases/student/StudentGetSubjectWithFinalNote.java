package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.StudentWithAllFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;

import java.util.List;

public interface StudentGetSubjectWithFinalNote {

    List<StudentWithAllFinalNoteModel> getAll();
    List<SubjectWithFinalNoteModel> getAllByStudentIdOrCode(Integer id, Integer code);


}
