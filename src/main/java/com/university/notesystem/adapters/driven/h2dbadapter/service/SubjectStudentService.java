package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.NoteMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectStudentMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.SimpleNoteProjection;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.SubjectStudentWithNotesProjection;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectStudentRepository;
import com.university.notesystem.domain.model.dtos.SimpleNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sun.tools.javac.resources.CompilerProperties.Notes.Note;
import static java.util.stream.Collectors.groupingBy;

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
    public List<SubjectWithNotesDTO> findAllSubjectWithNotesByStudent(int studentId) {
        // Agrupamos por el `id` del Subject.
        List<SubjectStudentWithNotesProjection> listSubjects = this.subjectStudentRepository.findAllByStudent(StudentEntity.builder().id(studentId).build());
        return listSubjects
                .stream()
                .map(entry -> {

                    Subject subject = SubjectMapper.mapToSubject(entry.getSubject());

                    return new SubjectWithNotesDTO(
                            subject.getId(),
                            subject.getName(),
                            entry.getNotes()
                                    .stream()
                                    .map(NoteMapper::mapToSimpleNoteDTO)
                                    .sorted(Comparator.comparingInt(SimpleNoteDTO::getNumber))
                                    .toList()
                    );
                })
                .collect(Collectors.toList());
    }

}
