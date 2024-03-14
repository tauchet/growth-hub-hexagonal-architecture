package com.university.notesystem.adapters.driven.h2dbdapter;

import com.university.notesystem.adapters.driven.h2dbadapter.config.H2DatabaseAdapter;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.ports.NotePort;
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
@Transactional
@DisplayName("Integración de puerto de notas.")
public class NotePortIntegrationTest {

    @Autowired
    private StudentPort studentPort;

    @Autowired
    private SubjectPort subjectPort;

    @Autowired
    private NotePort notePort;

    @Autowired
    private SubjectStudentPort subjectStudentPort;

    @Test
    @DisplayName("Guardar una nota.")
    public void saveNote() {

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

        subjectStudent = this.subjectStudentPort.save(subjectStudent);

        Note note = Note.builder()
                .note(3D)
                .number(1)
                .subjectStudent(subjectStudent)
                .build();

        this.notePort.save(note);

        // Checks
        Note reply = this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 1);
        Assertions.assertEquals(reply.getSubjectStudent().getId(), subjectStudent.getId());
        Assertions.assertEquals(reply.getNumber(), 1);
        Assertions.assertEquals(reply.getNote(), 3D);

    }

    @Test
    @DisplayName("Buscar una nota que no existe.")
    public void searchNoteWhenNotExists() {

        // Checks
        Note reply = this.notePort.findBySubjectStudentAndNumber(1, 1);
        Assertions.assertNull(reply);

    }

    @Test
    @DisplayName("Buscar una nota que exista.")
    public void searchNoteWhenExists() {

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

        subjectStudent = this.subjectStudentPort.save(subjectStudent);

        Note note = Note.builder()
                .note(3D)
                .number(1)
                .subjectStudent(subjectStudent)
                .build();

        this.notePort.save(note);

        // Checks
        Note reply = this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 1);
        Assertions.assertEquals(reply.getSubjectStudent().getId(), subjectStudent.getId());
        Assertions.assertEquals(reply.getNumber(), 1);
        Assertions.assertEquals(reply.getNote(), 3D);

    }

    @Test
    @DisplayName("Buscar todas las notas.")
    public void searchAllNotes() {

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

        subjectStudent = this.subjectStudentPort.save(subjectStudent);

        this.notePort.save(Note.builder()
                .note(3D)
                .number(1)
                .subjectStudent(subjectStudent)
                .build());

        this.notePort.save(Note.builder()
                .note(2D)
                .number(2)
                .subjectStudent(subjectStudent)
                .build());

        this.notePort.save(Note.builder()
                .note(5D)
                .number(3)
                .subjectStudent(subjectStudent)
                .build());

        // Checks
        List<Note> notes = this.notePort.findAll();
        Assertions.assertEquals(notes.size(), 3);

        Assertions.assertEquals(notes.get(0).getNumber(), 1);
        Assertions.assertEquals(notes.get(0).getNote(), 3D);
        Assertions.assertEquals(notes.get(0).getSubjectStudent().getId(), subjectStudent.getId());

        Assertions.assertEquals(notes.get(1).getNumber(), 2);
        Assertions.assertEquals(notes.get(1).getNote(), 2D);
        Assertions.assertEquals(notes.get(1).getSubjectStudent().getId(), subjectStudent.getId());

        Assertions.assertEquals(notes.get(2).getNumber(), 3);
        Assertions.assertEquals(notes.get(2).getNote(), 5D);
        Assertions.assertEquals(notes.get(2).getSubjectStudent().getId(), subjectStudent.getId());

    }


}
