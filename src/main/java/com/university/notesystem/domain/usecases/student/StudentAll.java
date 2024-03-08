package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.entities.Student;

import java.util.List;

public interface StudentAll {

    List<Student> findAll();

}
