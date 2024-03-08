package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.ports.StudentPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentDeleteImpl implements StudentDelete {

    private final StudentPort studentPort;

    @Override
    public void deleteById(int id) {
        this.studentPort.deleteById(id);
    }

}
