package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubjectAllImpl implements SubjectAll {

    private final SubjectPort subjectPort;

    @Override
    public List<Subject> findAll() {
        return this.subjectPort.findAll();
    }

}
