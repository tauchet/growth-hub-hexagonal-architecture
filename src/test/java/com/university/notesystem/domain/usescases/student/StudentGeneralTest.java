package com.university.notesystem.domain.usescases.student;

import com.university.notesystem.domain.exceptions.ResourceAlreadyExistsException;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.usecases.student.*;
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

@DisplayName("Métodos generales de los estudiantes.")
@ExtendWith(MockitoExtension.class)
public class StudentGeneralTest {

    @Mock
    private StudentPort studentPort;

    @InjectMocks
    private StudentGeneralManagerImpl studentGeneralManager;

    @Test
    @DisplayName("Creación de un estudiante.")
    public void onStudentCreate() {

        Student student = Student.builder()
                .id(1)
                .name("Cristian")
                .build();

        this.studentGeneralManager.register(student);

        // Mock
        Mockito.verify(this.studentPort, Mockito.times(1)).save(student);

    }

    @Test
    @DisplayName("Creación de un estudiante que ya existe su id o código.")
    public void onStudentCreateAlreadyExists() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        Mockito.when(this.studentPort.existsByIdOrCode(student.getId(), student.getCode())).thenReturn(true);
        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> this.studentGeneralManager.register(student));

    }

    @Test
    @DisplayName("Eliminanción de un estudiante.")
    public void onStudentDelete() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentGeneralManager.deleteById(student.getId());

        // Mock
        Mockito.verify(this.studentPort, Mockito.times(1)).deleteById(student.getId());

    }

    @Test
    @DisplayName("Obtener todos los estudiantes.")
    public void onGetAllStudents() {

        Student student = Student.builder()
                .id(1)
                .name("Cristian")
                .build();

        // Mock
        Mockito.when(this.studentPort.findAll()).thenReturn(Collections.singletonList(student));

        Assertions.assertNotNull(this.studentGeneralManager.findAll());
        Assertions.assertEquals(this.studentGeneralManager.findAll().size(), 1);

    }


}
