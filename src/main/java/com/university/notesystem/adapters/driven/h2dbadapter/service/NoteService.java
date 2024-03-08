package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.NoteMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.NoteRepository;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.ports.NotePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NoteService implements NotePort {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> findAll() {
        return this.noteRepository.findAll().stream().map(NoteMapper::mapToNote).collect(Collectors.toList());
    }

    @Override
    public void save(Note note) {
        this.noteRepository.save(NoteMapper.mapToNoteEntity(note));
    }

    @Override
    public Note findByRegisterAndNumber(Integer registerId, Integer number) {
        return this.noteRepository.findByRegisterAndNumber(
                SubjectStudentEntity.builder().id(registerId).build(),
                number
        ).map(NoteMapper::mapToNote).orElse(null);
    }

}
