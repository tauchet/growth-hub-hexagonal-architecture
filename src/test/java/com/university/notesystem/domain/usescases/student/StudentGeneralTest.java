package com.university.notesystem.domain.usescases.student;

import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.usecases.student.*;
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
public class StudentGeneralTest {

    @Mock
    private StudentPort studentPort;

    private StudentRegister studentRegister;
    private StudentDelete studentDelete;
    private StudentAll studentAll;

    @BeforeEach
    public void setUp() {
        this.studentRegister = new StudentRegisterImpl(this.studentPort);
        this.studentDelete = new StudentDeleteImpl(this.studentPort);
        this.studentAll = new StudentAllImpl(this.studentPort);
    }

    @Test
    @DisplayName("Creación de un estudiante.")
    public void onStudentCreate() {

        Student student = Student.builder()
                .id(1)
                .name("Cristian")
                .build();

        this.studentRegister.register(student);

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(1, 0)).thenReturn(student);

        Student reply = this.studentPort.getByIdOrCode(1, 0);
        Assertions.assertEquals(reply.getId(), student.getId());
        Assertions.assertEquals(reply.getName(), student.getName());
        Assertions.assertEquals(reply.getCode(), student.getCode());

    }

    @Test
    @DisplayName("Eliminanción de un estudiante.")
    public void onStudentDelete() {

        Student student = Student.builder()
                .id(1)
                .name("Cristian")
                .build();

        this.studentRegister.register(student);

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(1, 0)).thenReturn(student);
        Assertions.assertNotNull(this.studentPort.getByIdOrCode(1, 0));

        this.studentDelete.deleteById(student.getId());

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(1, 0)).thenReturn(null);
        Assertions.assertNull(this.studentPort.getByIdOrCode(1, 0));

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

        Assertions.assertNotNull(this.studentAll.findAll());
        Assertions.assertEquals(this.studentAll.findAll().size(), 1);

    }


}
