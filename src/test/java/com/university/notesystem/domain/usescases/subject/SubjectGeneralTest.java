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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class SubjectGeneralTest {

    @Mock
    private SubjectPort subjectPort;

    @InjectMocks
    private SubjectGeneralManagerImpl subjectGeneralManager;

    @Test
    @DisplayName("Creación de una asignatura.")
    public void onSubjectCreate() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectGeneralManager.register(subject);

        // Mock
        Mockito.verify(this.subjectPort, Mockito.times(1)).save(subject);

    }

    @Test
    @DisplayName("Eliminación de una asignatura.")
    public void onSubjectDelete() {

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectGeneralManager.deleteById(subject.getId());

        // Mock
        Mockito.verify(this.subjectPort, Mockito.times(1)).deleteById(subject.getId());

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

        Assertions.assertNotNull(this.subjectGeneralManager.findAll());
        Assertions.assertEquals(this.subjectGeneralManager.findAll().size(), 1);

    }


}
