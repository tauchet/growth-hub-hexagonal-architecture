package com.university.notesystem.adapters.driven.h2dbadapter.repository;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectStudentRepository extends JpaRepository<SubjectStudentEntity, Integer> {

    Optional<SubjectStudentEntity> getByStudentAndSubject(StudentEntity student, SubjectEntity subject);
    boolean existsByStudentAndSubject(StudentEntity student, SubjectEntity subject);

    List<SubjectStudentEntity> findAllByStudent(StudentEntity student);

}
