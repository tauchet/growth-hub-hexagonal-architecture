package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.SubjectStudentWithNotesModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.SubjectStudent;

import java.util.List;

public interface SubjectStudentPort {

    SubjectStudent save(SubjectStudent subject);
    SubjectStudent getByStudentIdAndSubjectId(int studentId, int subjectId);
    List<SubjectWithNotesModel> findAllSubjectWithNotesByStudent(int studentId);
    List<SubjectStudentWithNotesModel> findAllSubjectWithNotes();
    boolean existsByStudentAndSubject(int studentId, int subjectId);

}
