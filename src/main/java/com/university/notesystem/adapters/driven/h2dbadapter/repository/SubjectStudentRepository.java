package com.university.notesystem.adapters.driven.h2dbadapter.repository;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectStudentRepository extends JpaRepository<SubjectStudentEntity, Integer> {
}
