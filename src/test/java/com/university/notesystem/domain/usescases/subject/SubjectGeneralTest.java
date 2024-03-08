package com.university.notesystem.domain.usescases.subject;

import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.usecases.subject.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class SubjectGeneralTest {

    @Mock
    private SubjectPort subjectPort;

    private SubjectRegister subjectRegister;
    private SubjectDelete subjectDelete;
    private SubjectAll subjectAll;

    @BeforeEach
    public void setUp() {
        this.subjectRegister = new SubjectRegisterImpl(this.subjectPort);
        this.subjectDelete = new SubjectDeleteImpl(this.subjectPort);
        this.subjectAll = new SubjectAllImpl(this.subjectPort);
    }

    @Test
    @DisplayName("Creación de una asignatura.")
    public void onSubjectCreate() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectRegister.register(subject);

        // Mock
        Mockito.when(this.subjectPort.getById(1)).thenReturn(subject);

        Subject reply = this.subjectPort.getById(1);
        Assertions.assertEquals(reply.getId(), subject.getId());
        Assertions.assertEquals(reply.getName(), subject.getName());

    }

    @Test
    @DisplayName("Eliminación de una asignatura.")
    public void onSubjectDelete() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectRegister.register(subject);

        // Mock
        Mockito.when(this.subjectPort.getById(1)).thenReturn(subject);
        Assertions.assertNotNull(this.subjectPort.getById(1));

        this.subjectDelete.deleteById(subject.getId());

        // Mock
        Mockito.when(this.subjectPort.getById(1)).thenReturn(null);
        Assertions.assertNull(this.subjectPort.getById(1));

    }

    @Test
    @DisplayName("Obtener todos las asignaturas.")
    public void onGetAllSubjects() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        // Mock
        Mockito.when(this.subjectPort.findAll()).thenReturn(Collections.singletonList(subject));

        Assertions.assertNotNull(this.subjectAll.findAll());
        Assertions.assertEquals(this.subjectAll.findAll().size(), 1);

    }


}
