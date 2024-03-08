package com.university.notesystem.adapters.driven.h2dbadapter.mapper;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.NoteEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.SimpleNoteProjection;
import com.university.notesystem.domain.model.dtos.SimpleNoteDTO;
import com.university.notesystem.domain.model.entities.Note;

import java.util.Optional;

public class NoteMapper {

    public static SimpleNoteDTO mapToSimpleNoteDTO(SimpleNoteProjection note) {
        return SimpleNoteDTO.builder()
                .note(note.getNote())
                .number(note.getNumber())
                .id(note.getId())
                .build();
    }

    public static NoteEntity mapToNoteEntity(Note note) {
        return NoteEntity.builder()
                .id(note.getId())
                .number(note.getNumber())
                .note(note.getNote())
                .register(Optional.of(note.getRegister()).map(SubjectStudentMapper::mapToSubjectStudentEntity).orElse(null))
                .build();
    }

    public static Note mapToNote(NoteEntity noteEntity) {
        return Note.builder()
                .id(noteEntity.getId())
                .number(noteEntity.getNumber())
                .note(noteEntity.getNote())
                .register(Optional.of(noteEntity.getRegister()).map(SubjectStudentMapper::mapToSubjectStudent).orElse(null))
                .build();
    }

}
