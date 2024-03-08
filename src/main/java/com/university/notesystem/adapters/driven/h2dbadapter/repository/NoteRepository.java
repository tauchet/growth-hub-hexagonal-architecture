package com.university.notesystem.adapters.driven.h2dbadapter.repository;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.NoteEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.StudentEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;
import com.university.notesystem.adapters.driven.h2dbadapter.projections.NoteWithSubjectProjection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<NoteEntity, Integer> {

    Optional<NoteEntity> findByStudentAndSubjectAndNumber(StudentEntity student, SubjectEntity subject, Integer number);
    List<NoteWithSubjectProjection> findAllByStudent(StudentEntity student);

}
