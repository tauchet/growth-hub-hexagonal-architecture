package com.university.notesystem.domain.usescases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.model.request.NoteEntryRequest;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.subject.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class SubjectRegisterStudentTest {

    @Mock
    private SubjectPort subjectPort;

    @Mock
    private SubjectStudentPort subjectStudentPort;

    @Mock
    private StudentPort studentPort;

    @Mock
    private NotePort notePort;

    private SubjectRegisterStudent subjectRegisterStudent;

    @BeforeEach
    public void setUp() {
        this.subjectRegisterStudent = new SubjectRegisterStudentImpl(this.studentPort,
                this.subjectPort,
                this.subjectStudentPort,
                this.notePort);
    }

    @Test
    @DisplayName("Registrar un estudiante sin notas.")
    public void onSubjectRegisterWithoutNotesStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(student.getId(), student.getCode())).thenReturn(student);
        Mockito.when(this.subjectPort.existsById(subject.getId())).thenReturn(true);
        Mockito.when(this.subjectStudentPort.existsByStudentAndSubject(student.getId(), subject.getId())).thenReturn(false);

        SubjectRegisterStudentRequest request = SubjectRegisterStudentRequest.builder()
                .studentId(student.getId())
                .subjectId(subject.getId())
                .notes(null)
                .build();

        this.subjectRegisterStudent.register(request);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .id(1)
                .build();

        // Mock
        Mockito.when(this.subjectStudentPort.getByStudentIdOrCodeAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);
        Mockito.when(this.subjectStudentPort.existsByStudentAndSubject(student.getId(), subject.getId())).thenReturn(true);


        Assertions.assertNotNull(this.subjectStudentPort.getByStudentIdOrCodeAndSubjectId(student.getId(), subject.getId()));




        Assertions.assertThrows(FieldException.class, () -> this.subjectRegisterStudent.register(request));


    }

    @Test
    @DisplayName("Registrar un estudiante con notas.")
    public void onSubjectRegisterConNotesStudent() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        Subject subject = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(student.getId(), student.getCode())).thenReturn(student);
        Mockito.when(this.subjectPort.existsById(subject.getId())).thenReturn(true);
        Mockito.when(this.subjectStudentPort.existsByStudentAndSubject(student.getId(), subject.getId())).thenReturn(false);

        SubjectRegisterStudentRequest request = SubjectRegisterStudentRequest.builder()
                .studentId(student.getId())
                .subjectId(subject.getId())
                .notes(Arrays.asList(
                        new NoteEntryRequest(1, 0.5),
                        new NoteEntryRequest(2, 1.5)
                ))
                .build();

        this.subjectRegisterStudent.register(request);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .student(student)
                .subject(subject)
                .id(1)
                .build();

        // Mock
        Mockito.when(this.subjectStudentPort.existsByStudentAndSubject(student.getId(), subject.getId())).thenReturn(true);
        Mockito.when(this.subjectStudentPort.getByStudentIdOrCodeAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);
        Mockito.when(this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 1)).thenReturn(Note.builder().number(1).note(0.5).subjectStudent(subjectStudent).id(1).build());
        Mockito.when(this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 2)).thenReturn(Note.builder().number(2).note(1.5).subjectStudent(subjectStudent).id(2).build());

        Assertions.assertNotNull(this.subjectStudentPort.getByStudentIdOrCodeAndSubjectId(student.getId(), subject.getId()));
        Assertions.assertThrows(FieldException.class, () -> this.subjectRegisterStudent.register(request));
        Assertions.assertEquals(this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 1).getNote(), 0.5);
        Assertions.assertEquals(this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), 2).getNote(), 1.5);


    }


}
