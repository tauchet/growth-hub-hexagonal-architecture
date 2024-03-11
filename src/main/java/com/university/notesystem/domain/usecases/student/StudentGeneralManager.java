package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;

import java.util.List;

public interface StudentGeneralManager {

    List<Student> findAll();
    void register(Student student);
    void deleteById(int id);

}
