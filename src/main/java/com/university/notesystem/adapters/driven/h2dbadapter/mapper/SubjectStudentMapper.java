package com.university.notesystem.adapters.driven.h2dbadapter.mapper;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import com.university.notesystem.domain.model.entities.SubjectStudent;

import java.util.Optional;

public class SubjectStudentMapper {

    public static SubjectStudentEntity mapToSubjectStudentEntity(SubjectStudent subject) {
        return SubjectStudentEntity.builder()
                .id(subject.getId())
                .student(Optional.of(subject.getStudent()).map(StudentMapper::mapToStudentEntity).orElse(null))
                .subject(Optional.of(subject.getSubject()).map(SubjectMapper::mapToSubjectEntity).orElse(null))
                .build();
    }

    public static SubjectStudent mapToSubjectStudent(SubjectStudentEntity subject) {
        return SubjectStudent.builder()
                .id(subject.getId())
                .student(Optional.of(subject.getStudent()).map(StudentMapper::mapToStudent).orElse(null))
                .subject(Optional.of(subject.getSubject()).map(SubjectMapper::mapToSubject).orElse(null))
                .build();
    }

}
