package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.NoteMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.StudentMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectStudentMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectStudentRepository;
import com.university.notesystem.domain.model.dtos.SimpleNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectStudentWithNotesDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SubjectStudentService implements SubjectStudentPort {

    private final SubjectStudentRepository subjectStudentRepository;

    @Override
    public SubjectStudent getByStudentIdOrCodeAndSubjectId(int studentId, int subjectId) {
        return this.subjectStudentRepository.getByStudentAndSubject(
                StudentEntity.builder().id(studentId).build(),
                SubjectEntity.builder().id(subjectId).build()
        ).map(SubjectStudentMapper::mapToSubjectStudent).orElse(null);
    }

    @Override
    public SubjectStudent save(SubjectStudent subject) {
        return SubjectStudentMapper.mapToSubjectStudent(this.subjectStudentRepository.save(SubjectStudentMapper.mapToSubjectStudentEntity(subject)));
    }

    @Override
    public List<SubjectStudentWithNotesDTO> findAllSubjectWithNotes() {
        return this.subjectStudentRepository
                .findAll()
                .stream()
                .map(subjectStudent -> new SubjectStudentWithNotesDTO(
                        subjectStudent.getId(),
                        Optional.of(subjectStudent.getSubject()).map(SubjectMapper::mapToSubject).orElse(null),
                        Optional.of(subjectStudent.getStudent()).map(StudentMapper::mapToStudent).orElse(null),
                        subjectStudent.getNotes().stream().map(NoteMapper::mapToNote).toList()
                ))
                .toList();
    }

    @Override
    public List<SubjectWithNotesDTO> findAllSubjectWithNotesByStudent(int studentId) {
        List<SubjectStudentEntity> listSubjects = this.subjectStudentRepository.findAllByStudent(StudentEntity.builder().id(studentId).build());
        return listSubjects
                .stream()
                .map(subject -> {

                    System.out.println(subject.getNotes());

                    return new SubjectWithNotesDTO(
                            subject.getSubject().getId(),
                            subject.getSubject().getName(),
                            subject.getNotes()
                                    .stream()
                                    .map(NoteMapper::mapToSimpleNoteDTO)
                                    .sorted(Comparator.comparingInt(SimpleNoteDTO::getNumber))
                                    .toList()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsByStudentAndSubject(int studentId, int subjectId) {
        return this.subjectStudentRepository.existsByStudentAndSubject(
                StudentEntity.builder().id(studentId).build(),
                SubjectEntity.builder().id(subjectId).build()
        );
    }
}
