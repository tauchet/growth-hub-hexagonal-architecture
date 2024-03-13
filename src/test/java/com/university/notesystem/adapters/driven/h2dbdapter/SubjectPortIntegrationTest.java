package com.university.notesystem.adapters.driven.h2dbdapter;

import com.university.notesystem.adapters.driven.h2dbadapter.config.H2DatabaseAdapter;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.infrastructure.application.NoteSystemApplication;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = { NoteSystemApplication.class, H2DatabaseAdapter.class })
@SpringBootConfiguration
@Transactional
@DisplayName("Integración de puerto de asignatura.")
public class SubjectPortIntegrationTest {

    @Autowired
    private SubjectPort subjectPort;

    @Test
    @DisplayName("Guardar una asignatura.")
    public void saveSubject() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        // Checks
        Subject reply = this.subjectPort.getById(subject.getId());
        Assertions.assertEquals(reply.getId(), subject.getId());
        Assertions.assertEquals(reply.getName(), subject.getName());

    }

    @Test
    @DisplayName("No debe existir una asignatura anterior no ingresada.")
    public void checkSubjectNotExists() {

        // Checks
        Boolean replyById = this.subjectPort.existsById(1);
        Assertions.assertEquals(replyById, false);


    }

    @Test
    @DisplayName("Debe existir una asignatura anteriormente creada.")
    public void checkSubjectExists() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        // Checks
        Boolean replyById = this.subjectPort.existsById(1);
        Assertions.assertEquals(replyById, true);

    }

    @Test
    @DisplayName("Buscar asignatura por id que no existe.")
    public void findSubjectByIdWhenNotExists() {

        // Checks
        Subject replyById = this.subjectPort.getById(1);
        Assertions.assertNull(replyById);

    }

    @Test
    @DisplayName("Buscar asignatura por id.")
    public void findSubjectById() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        // Checks
        Subject replyById = this.subjectPort.getById(subject.getId());
        Assertions.assertEquals(replyById.getId(), subject.getId());
        Assertions.assertEquals(replyById.getName(), subject.getName());

    }

    @Test
    @DisplayName("Eliminar una asignatura.")
    public void deleteSubject() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);
        this.subjectPort.deleteById(subject.getId());

        // Checks
        Subject reply = this.subjectPort.getById(subject.getId());
        Assertions.assertNull(reply);

    }

    @Test
    @DisplayName("Obtener todas las asignaturas.")
    public void findAllSubject() {

        this.subjectPort.save(Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build());

        this.subjectPort.save(Subject.builder()
                .id(2)
                .name("Programación")
                .build());

        // Checks
        List<Subject> reply = this.subjectPort.findAll();
        Assertions.assertEquals(reply.size(), 2);

    }

}
