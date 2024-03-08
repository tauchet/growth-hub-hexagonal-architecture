package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubjectRegisterImpl implements SubjectRegister {

    private final SubjectPort subjectPort;

    @Override
    public void register(Subject subject) {

        if (this.subjectPort.existsById(subject.getId())) {
            throw new FieldException("id", "¡La id ya se encuentra ocupada!");
        }

        this.subjectPort.save(subject);
    }

}
