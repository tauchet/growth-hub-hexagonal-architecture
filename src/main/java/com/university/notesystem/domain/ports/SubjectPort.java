package com.university.notesystem.domain.ports;

import com.university.notesystem.domain.model.entities.Subject;

import java.util.List;

public interface SubjectPort {

    Subject getById(int id);
    void save(Subject subject);
    boolean existsById(int id);
    void deleteById(int id);
    List<Subject> findAll();

}
