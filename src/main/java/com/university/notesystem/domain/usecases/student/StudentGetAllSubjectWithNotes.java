package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.SubjectWithNotesModel;

import java.util.List;

public interface StudentGetAllSubjectWithNotes {

    List<SubjectWithNotesModel> getAllByIdOrCode(Integer id, Integer code);

}
