package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.entities.Subject;

import java.util.List;

public interface SubjectGeneralManager {

    List<Subject> findAll();
    void register(Subject subject);
    void deleteById(int id);


}
