package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.mapper.SubjectMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectRepository;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SubjectService implements SubjectPort {

    private final SubjectRepository subjectRepository;

    @Override
    public Subject getById(int id) {
        return this.subjectRepository.findById(id).map(SubjectMapper::mapToSubject).orElse(null);
    }

    @Override
    public void save(Subject subject) {
        this.subjectRepository.save(SubjectMapper.mapToSubjectEntity(subject));
    }

    @Override
    public void deleteById(int id) {
        this.subjectRepository.deleteById(id);
    }

    @Override
    public boolean existsById(int id) {
        return this.subjectRepository.existsById(id);
    }

    @Override
    public List<Subject> findAll() {
        return this.subjectRepository.findAll().stream().map(SubjectMapper::mapToSubject).collect(Collectors.toList());
    }

}
