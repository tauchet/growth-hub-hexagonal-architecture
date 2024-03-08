package com.university.notesystem.adapters.driven.h2dbadapter.repository;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.NoteEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectStudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    Optional<NoteEntity> findBySubjectStudentAndNumber(SubjectStudentEntity subjectStudent, Integer number);

}
