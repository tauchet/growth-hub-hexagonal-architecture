package com.university.notesystem.adapters.driven.h2dbadapter.mapper;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.NoteEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.NoteWithSubjectProjection;
import com.university.notesystem.domain.model.dtos.SimpleNoteDTO;
import com.university.notesystem.domain.model.entities.Note;

import java.util.Optional;

public class NoteMapper {

    public static SimpleNoteDTO mapToSimpleNoteDTO(NoteWithSubjectProjection note) {
        return SimpleNoteDTO.builder()
                .note(note.getNote())
                .number(note.getNumber())
                .id(note.getId())
                .build();
    }

    public static NoteEntity mapToNoteEntity(Note note) {
        return NoteEntity.builder()
                .id(note.getId())
                .student(Optional.of(note.getStudent()).map(StudentMapper::mapToStudentEntity).orElse(null))
                .subject(Optional.of(note.getSubject()).map(SubjectMapper::mapToSubjectEntity).orElse(null))
                .number(note.getNumber())
                .note(note.getNote())
                .build();
    }

    public static Note mapToNote(NoteEntity noteEntity) {
        return Note.builder()
                .id(noteEntity.getId())
                .student(Optional.of(noteEntity.getStudent()).map(StudentMapper::mapToStudent).orElse(null))
                .subject(Optional.of(noteEntity.getSubject()).map(SubjectMapper::mapToSubject).orElse(null))
                .number(noteEntity.getNumber())
                .note(noteEntity.getNote())
                .build();
    }

}