package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceAlreadyExistsException;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.usecases.UseCase;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentGeneralManagerImpl implements StudentGeneralManager, UseCase {

    private final StudentPort studentPort;

    @Override
    public List<Student> findAll() {
        return this.studentPort.findAll();
    }

    @Override
    public void register(Student student) {

        if (this.studentPort.existsByIdOrCode(student.getId(), student.getCode())) {
            throw new ResourceAlreadyExistsException("id", "¡La id o código ya se encuentra ocupado!");
        }

        this.studentPort.save(student);
    }

    @Override
    public void deleteById(int id) {
        this.studentPort.deleteById(id);
    }

}
