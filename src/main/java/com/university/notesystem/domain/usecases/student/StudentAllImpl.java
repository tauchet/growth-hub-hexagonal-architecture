package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentAllImpl implements StudentAll {

    private final StudentPort studentPort;

    @Override
    public List<Student> findAll() {
        return this.studentPort.findAll();
    }

}
