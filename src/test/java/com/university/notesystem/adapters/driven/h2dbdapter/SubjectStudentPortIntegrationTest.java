package com.university.notesystem.adapters.driven.h2dbdapter;

import com.university.notesystem.adapters.driven.h2dbadapter.config.H2DatabaseAdapter;
import com.university.notesystem.domain.model.SubjectStudentWithNotesModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
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
@DisplayName("Integración de puerto de asignatura y estudiante.")
public class SubjectStudentPortIntegrationTest {

    @Autowired
    private StudentPort studentPort;

    @Autowired
    private SubjectPort subjectPort;

    @Autowired
    private SubjectStudentPort subjectStudentPort;

    @Test
    @DisplayName("Guardar un registro de estudiante.")
    public void registerStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .build();

        SubjectStudent reply = this.subjectStudentPort.save(subjectStudent);
        Assertions.assertEquals(reply.getStudent().getId(), student.getId());
        Assertions.assertEquals(reply.getSubject().getId(), subject.getId());

    }

    @Test
    @DisplayName("Verificar un registro de estudiante que no existe.")
    public void validStudentNotExists() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        boolean reply = this.subjectStudentPort.existsByStudentAndSubject(student.getId(), subject.getId());
        Assertions.assertFalse(reply);

    }

    @Test
    @DisplayName("Obtener un registro de estudiante que no existe.")
    public void findSubjectStudentNotExists() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        SubjectStudent reply = this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId());
        Assertions.assertNull(reply);

    }

    @Test
    @DisplayName("Obtener un registro por id de estudiante que existe.")
    public void findSubjectStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        this.subjectPort.save(subject);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .build();
        this.subjectStudentPort.save(subjectStudent);

        SubjectStudent reply = this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId());
        Assertions.assertNotNull(reply);

    }

    @Test
    @DisplayName("Obtener registro de asignaturas de un estudiante.")
    public void findSubjectStudentByStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();
        this.subjectPort.save(subject);

        Subject subject2 = Subject.builder()
                .id(2)
                .name("Programación")
                .build();
        this.subjectPort.save(subject2);

        this.subjectStudentPort.save(SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .build());

        this.subjectStudentPort.save(SubjectStudent.builder()
                .student(student)
                .subject(subject2)
                .build());

        List<SubjectWithNotesModel> reply = this.subjectStudentPort.findAllSubjectWithNotesByStudent(student.getId());
        Assertions.assertEquals(reply.size(), 2);
        Assertions.assertEquals(reply.get(0).getId(), subject.getId());
        Assertions.assertEquals(reply.get(0).getName(), subject.getName());
        Assertions.assertEquals(reply.get(1).getId(), subject2.getId());
        Assertions.assertEquals(reply.get(1).getName(), subject2.getName());

    }

    @Test
    @DisplayName("Obtener registro de asignaturas de todos los estudiantes.")
    public void findAllSubjectStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        this.studentPort.save(student);

        Student student2 = Student.builder()
                .id(2)
                .code(2)
                .name("Camilo")
                .build();
        this.studentPort.save(student2);

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();
        this.subjectPort.save(subject);

        Subject subject2 = Subject.builder()
                .id(2)
                .name("Programación")
                .build();
        this.subjectPort.save(subject2);

        this.subjectStudentPort.save(SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .build());

        this.subjectStudentPort.save(SubjectStudent.builder()
                .student(student)
                .subject(subject2)
                .build());

        this.subjectStudentPort.save(SubjectStudent.builder()
                .student(student2)
                .subject(subject)
                .build());

        List<SubjectStudentWithNotesModel> reply = this.subjectStudentPort.findAllSubjectWithNotes();
        Assertions.assertEquals(reply.size(), 3);

        Assertions.assertEquals(reply.get(0).getStudent().getId(), student.getId());
        Assertions.assertEquals(reply.get(0).getSubject().getId(), subject.getId());

        Assertions.assertEquals(reply.get(1).getStudent().getId(), student.getId());
        Assertions.assertEquals(reply.get(1).getSubject().getId(), subject2.getId());

        Assertions.assertEquals(reply.get(2).getStudent().getId(), student2.getId());
        Assertions.assertEquals(reply.get(2).getSubject().getId(), subject.getId());

    }

}
