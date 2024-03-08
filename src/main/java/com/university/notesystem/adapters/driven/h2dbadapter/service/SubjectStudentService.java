package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectStudentMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectStudentRepository;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubjectStudentService implements SubjectStudentPort {

    private final SubjectStudentRepository subjectStudentRepository;

    @Override
    public void save(SubjectStudent subject) {
        this.subjectStudentRepository.save(SubjectStudentMapper.mapToSubjectStudentEntity(subject));
    }

}
