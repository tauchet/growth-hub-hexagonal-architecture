package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Note;

import java.util.List;

public interface NotePort {

    List<Note> findAll();
    void save(Note note);
    Note findByRegisterAndNumber(Integer registerId, Integer number);


}
