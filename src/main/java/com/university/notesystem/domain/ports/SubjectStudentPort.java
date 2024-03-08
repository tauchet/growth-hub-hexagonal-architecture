package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.dtos.SubjectStudentWithNotesDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.SubjectStudent;

import java.util.List;

public interface SubjectStudentPort {

    SubjectStudent save(SubjectStudent subject);
    SubjectStudent getByStudentIdOrCodeAndSubjectId(int studentId, int subjectId);
    List<SubjectWithNotesDTO> findAllSubjectWithNotesByStudent(int studentId);
    List<SubjectStudentWithNotesDTO> findAllSubjectWithNotes();
    boolean existsByStudentAndSubject(int studentId, int subjectId);

}
