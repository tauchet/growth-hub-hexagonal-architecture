package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentRegisterImpl implements StudentRegister {

    private final StudentPort studentPort;

    @Override
    public void register(Student student) {

        if (this.studentPort.existsByIdOrCode(student.getId(), student.getCode())) {
            throw new FieldException("id", "¡La id o código ya se encuentra ocupado!");
        }

        this.studentPort.save(student);
    }


}
