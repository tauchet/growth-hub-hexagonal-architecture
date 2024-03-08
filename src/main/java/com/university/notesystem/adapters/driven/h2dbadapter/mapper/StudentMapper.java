package com.university.notesystem.adapters.driven.h2dbadapter.mapper;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.domain.model.entities.Student;

public class StudentMapper {

    public static StudentEntity mapToStudentEntity(Student student) {
        return StudentEntity.builder()
                .id(student.getId())
                .name(student.getName())
                .code(student.getCode())
                .build();
    }

    public static Student mapToStudent(StudentEntity studentEntity) {
        return Student.builder()
                .id(studentEntity.getId())
                .name(studentEntity.getName())
                .code(studentEntity.getCode())
                .build();
    }

}
