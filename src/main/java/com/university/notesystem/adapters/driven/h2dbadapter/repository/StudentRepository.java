package com.university.notesystem.adapters.driven.h2dbadapter.repository;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.domain.model.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {

    Student findByIdOrCode(Integer id, Integer code);
    boolean existsByIdOrCode(Integer id, Integer code);

}
