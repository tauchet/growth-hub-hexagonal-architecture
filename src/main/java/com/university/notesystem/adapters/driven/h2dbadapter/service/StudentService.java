package com.university.notesystem.adapters.driven.h2dbadapter.service;

import com.university.notesystem.adapters.driven.h2dbadapter.mapper.StudentMapper;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.StudentRepository;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StudentService implements StudentPort {

    private final StudentRepository studentRepository;

    @Override
    public Student getByIdOrCode(Integer id, Integer code) {
        return this.studentRepository.findByIdOrCode(id, code);
    }

    @Override
    public void save(Student student) {
        this.studentRepository.save(StudentMapper.mapToStudentEntity(student));
    }

    @Override
    public void deleteById(int id) {
        this.studentRepository.deleteById(id);
    }

    @Override
    public boolean existsByIdOrCode(Integer id, Integer code) {
        return this.studentRepository.existsByIdOrCode(id, code);
    }

    @Override
    public List<Student> findAll() {
        return this.studentRepository.findAll().stream().map(StudentMapper::mapToStudent).collect(Collectors.toList());
    }

}
