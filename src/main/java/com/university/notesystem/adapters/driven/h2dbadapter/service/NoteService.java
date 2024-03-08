package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.NoteMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.NoteWithSubjectProjection;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.NoteRepository;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.NotePort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class NoteService implements NotePort {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> findAll() {
        return this.noteRepository.findAll().stream().map(NoteMapper::mapToNote).collect(Collectors.toList());
    }

    @Override
    public Note findByStudentAndSubjectAndNumber(int studentId, int subjectId, int number) {
        return this.noteRepository.findByStudentAndSubjectAndNumber(
                StudentEntity.builder().id(subjectId).build(),
                SubjectEntity.builder().id(subjectId).build(),
                number)
                .map(NoteMapper::mapToNote).orElse(null);
    }

    @Override
    public void save(Note note) {
        this.noteRepository.save(NoteMapper.mapToNoteEntity(note));
    }

    @Override
    public void delete(Note note) {
        this.noteRepository.delete(NoteMapper.mapToNoteEntity(note));
    }

    @Override
    public List<SubjectWithNotesDTO> findAllSubjectWithNotesByStudent(int studentId) {

        // Agrupamos por el `id` del Subject.
        Map<Integer, List<NoteWithSubjectProjection>> mapBySubjectId = this.noteRepository
                .findAllByStudent(StudentEntity.builder().id(studentId).build())
                .stream()
                .collect(groupingBy(entry -> entry.getSubject().getId()));

        return mapBySubjectId
                .values()
                .stream()
                .map(entry -> {
                    Subject subject = SubjectMapper.mapToSubject(entry.get(0).getSubject());
                    return new SubjectWithNotesDTO(
                            subject.getId(),
                            subject.getName(),
                            entry
                                    .stream()
                                    .sorted(Comparator.comparingInt(NoteWithSubjectProjection::getNumber))
                                    .map(NoteMapper::mapToSimpleNoteDTO)
                                    .collect(Collectors.toList())
                    );
                })
                .collect(Collectors.toList());

    }

}
