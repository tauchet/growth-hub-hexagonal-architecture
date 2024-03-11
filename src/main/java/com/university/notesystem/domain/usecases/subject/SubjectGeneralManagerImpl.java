package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SubjectGeneralManagerImpl implements SubjectGeneralManager {

    private final SubjectPort subjectPort;

    @Override
    public List<Subject> findAll() {
        return this.subjectPort.findAll();
    }

    @Override
    public void register(Subject subject) {

        if (this.subjectPort.existsById(subject.getId())) {
            throw new FieldException("id", "¡La id ya se encuentra ocupada!");
        }

        this.subjectPort.save(subject);

    }

    @Override
    public void deleteById(int id) {
        this.subjectPort.deleteById(id);
    }

}
