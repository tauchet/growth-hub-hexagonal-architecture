package com.university.notesystem.adapters.driven.h2dbadapter.mapper;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.domain.model.entities.Subject;

public class SubjectMapper {

    public static SubjectEntity mapToSubjectEntity(Subject subject) {
        return SubjectEntity.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

    public static Subject mapToSubject(SubjectEntity subject) {
        return Subject.builder()
                .id(subject.getId())
                .name(subject.getName())
                .build();
    }

}
