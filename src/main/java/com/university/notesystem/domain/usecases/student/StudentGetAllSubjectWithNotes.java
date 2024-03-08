package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;

import java.util.List;

public interface StudentGetAllSubjectWithNotes {

    List<SubjectWithNotesDTO> getAllByIdOrCode(Integer id, Integer code);

}
