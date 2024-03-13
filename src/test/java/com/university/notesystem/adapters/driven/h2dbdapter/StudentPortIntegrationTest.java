package com.university.notesystem.adapters.driven.h2dbdapter;

import com.university.notesystem.adapters.driven.h2dbadapter.config.H2DatabaseAdapter;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
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
@DisplayName("Integración de puerto de estudiante.")
public class StudentPortIntegrationTest {

    @Autowired
    private StudentPort studentPort;


    @Test
    @DisplayName("Guardar un estudiante.")
    public void onStudentCreateOrGet() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        // Checks
        Student reply = this.studentPort.getByIdOrCode(student.getId(), student.getCode());
        Assertions.assertEquals(reply.getId(), student.getId());
        Assertions.assertEquals(reply.getCode(), student.getCode());
        Assertions.assertEquals(reply.getName(), student.getName());

    }

    @Test
    @DisplayName("No debe existir un usuario anterior no ingresado.")
    public void onStudentNotExists() {

        // Checks
        Boolean replyById = this.studentPort.existsByIdOrCode(1, null);
        Assertions.assertEquals(replyById, false);

        Boolean replyByCode = this.studentPort.existsByIdOrCode(null, 1);
        Assertions.assertEquals(replyByCode, false);

    }

    @Test
    @DisplayName("Debe existir un usuario anteriormente creado.")
    public void onStudentExists() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        // Checks
        Boolean replyById = this.studentPort.existsByIdOrCode(1, null);
        Assertions.assertEquals(replyById, true);

        Boolean replyByCode = this.studentPort.existsByIdOrCode(null, 1);
        Assertions.assertEquals(replyByCode, true);

    }

    @Test
    @DisplayName("Buscar estudiante por id o codigo que no existe.")
    public void onStudentGetByIdOrCodeNotExists() {

        // Checks
        Student replyById = this.studentPort.getByIdOrCode(1, null);
        Assertions.assertNull(replyById);

        Student replyByCode = this.studentPort.getByIdOrCode(null, 1);
        Assertions.assertNull(replyByCode);

    }

    @Test
    @DisplayName("Buscar estudiante por id o codigo")
    public void onStudentGetByIdOrCode() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        // Checks
        Student replyById = this.studentPort.getByIdOrCode(student.getId(), null);
        Assertions.assertEquals(replyById.getId(), student.getId());
        Assertions.assertEquals(replyById.getCode(), student.getCode());
        Assertions.assertEquals(replyById.getName(), student.getName());

        Student replyByCode = this.studentPort.getByIdOrCode(null, student.getCode());
        Assertions.assertEquals(replyByCode.getId(), student.getId());
        Assertions.assertEquals(replyByCode.getCode(), student.getCode());
        Assertions.assertEquals(replyByCode.getName(), student.getName());

    }

    @Test
    @DisplayName("Eliminar un estudiante.")
    public void onStudentRemove() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);
        this.studentPort.deleteById(student.getId());

        // Checks
        Student reply = this.studentPort.getByIdOrCode(student.getId(), student.getCode());
        Assertions.assertNull(reply);

    }

    @Test
    @DisplayName("Obtener todos los estudiantes.")
    public void onGetAllStudent() {


        this.studentPort.save(Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build());

        this.studentPort.save(Student.builder()
                .id(2)
                .code(2)
                .name("Camilo")
                .build());

        // Checks
        List<Student> reply = this.studentPort.findAll();
        Assertions.assertEquals(reply.size(), 2);

    }

}
