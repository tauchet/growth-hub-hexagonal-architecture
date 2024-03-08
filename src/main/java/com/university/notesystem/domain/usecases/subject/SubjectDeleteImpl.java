package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.usecases.student.StudentDelete;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubjectDeleteImpl implements SubjectDelete {

    private final SubjectPort subjectPort;

    @Override
    public void deleteById(int id) {
        this.subjectPort.deleteById(id);
    }

}
