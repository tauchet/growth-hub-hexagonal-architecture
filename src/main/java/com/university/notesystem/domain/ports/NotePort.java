package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Note;

import java.util.List;

public interface NotePort {

    List<Note> findAll();
    void save(Note note);
    void delete(Note note);
    Note findByStudentAndSubjectAndNumber(int studentId, int subjectId, int number);

    List<SubjectWithNotesDTO> findAllSubjectWithNotesByStudent(int studentId);

}
