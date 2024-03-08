package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Student;

import java.util.List;

public interface StudentPort {

    Student getByIdOrCode(Integer id, Integer code);
    void save(Student student);
    void deleteById(int id);
    boolean existsByIdOrCode(Integer id, Integer code);
    List<Student> findAll();

}
