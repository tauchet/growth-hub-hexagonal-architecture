package com.university.notesystem.domain.usescases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceAlreadyExistsException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.model.request.EntryNoteRequest;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.subject.SubjectRegisterStudent;
import com.university.notesystem.domain.usecases.subject.SubjectRegisterStudentImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

@DisplayName("Registrar un estudiante a una asignatura.")
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

    @InjectMocks
    private SubjectRegisterStudentImpl subjectRegisterStudent;

    @Test
    @DisplayName("El estudiante no existe.")
    public void onSubjectRegisterStudentWithoutStudent() {

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(null);

        SubjectRegisterStudentRequest request = SubjectRegisterStudentRequest.builder()
                .studentId(1)
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.subjectRegisterStudent.register(request));

    }

    @Test
    @DisplayName("La asignatura no existe.")
    public void onSubjectRegisterStudentWithoutSubject() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(student.getId(), student.getCode())).thenReturn(student);
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(false);

        SubjectRegisterStudentRequest request = SubjectRegisterStudentRequest.builder()
                .studentId(1)
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.subjectRegisterStudent.register(request));

    }

    @Test
    @DisplayName("El estudiante ya ha sido registrado en la asignatura.")
    public void onSubjectRegisterStudentWhenExists() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(student.getId(), student.getCode())).thenReturn(student);
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);
        Mockito.when(this.subjectStudentPort.existsByStudentAndSubject(student.getId(), 1)).thenReturn(true);

        SubjectRegisterStudentRequest request = SubjectRegisterStudentRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceAlreadyExistsException.class, () -> this.subjectRegisterStudent.register(request));

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

        ArgumentCaptor<SubjectStudent> reply = ArgumentCaptor.forClass(SubjectStudent.class);
        Mockito.verify(this.subjectStudentPort, Mockito.times(1)).save(reply.capture());

        Assertions.assertEquals(reply.getValue().getStudent().getId(), student.getId());
        Assertions.assertEquals(reply.getValue().getSubject().getId(), subject.getId());

    }

    @Test
    @DisplayName("Registrar un estudiante con más de 3 notas.")
    public void onSubjectRegisterWithLimitNotesStudent() {

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
                        new EntryNoteRequest(1, 1D),
                        new EntryNoteRequest(2, 5D),
                        new EntryNoteRequest(3, 4D),
                        new EntryNoteRequest(4, 1D)
                ))
                .build();


        Assertions.assertThrows(FieldException.class, () -> this.subjectRegisterStudent.register(request));

    }

    @Test
    @DisplayName("Registrar un estudiante con notas.")
    public void onSubjectRegisterStudent() {

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
                        new EntryNoteRequest(1, 1D),
                        new EntryNoteRequest(2, 5D),
                        new EntryNoteRequest(3, 4D)
                ))
                .build();

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .id(0)
                .student(Student.builder().id(student.getId()).build())
                .subject(Subject.builder().id(subject.getId()).build())
                .build();

        Mockito.when(this.subjectStudentPort.save(Mockito.any(SubjectStudent.class))).thenReturn(subjectStudent);
        this.subjectRegisterStudent.register(request);

        ArgumentCaptor<Note> reply = ArgumentCaptor.forClass(Note.class);
        Mockito.verify(this.notePort, Mockito.times(3)).save(reply.capture());

        // Verify notes
        List<Note> replyNotes = reply.getAllValues();
        for (int i = 0; i < request.getNotes().size(); ++i) {
            EntryNoteRequest entryNoteRequest = request.getNotes().get(i);
            Note note = replyNotes.get(i);
            Assertions.assertEquals(note.getSubjectStudent().getId(), subjectStudent.getId());
            Assertions.assertEquals(note.getNumber(), entryNoteRequest.getNumber());
            Assertions.assertEquals(note.getNote(), entryNoteRequest.getValue());
        }

    }


}
