package com.university.notesystem.domain.usescases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.model.request.EntryNoteRequest;
import com.university.notesystem.domain.model.request.SubjectUpdateStudentNotesRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.subject.SubjectUpdateStudentNotesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@DisplayName("Actualizar notas de un estudiante.")
@ExtendWith(MockitoExtension.class)
public class SubjectUpdateStudentNotesTest {

    @Mock
    private SubjectPort subjectPort;

    @Mock
    private SubjectStudentPort subjectStudentPort;

    @Mock
    private StudentPort studentPort;

    @Mock
    private NotePort notePort;


    @InjectMocks
    private SubjectUpdateStudentNotesImpl subjectUpdateStudentNotes;

    @Test
    @DisplayName("El estudiante no existe.")
    public void onSubjectUpdateStudentWithoutStudent() {

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(null);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(1)
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.subjectUpdateStudentNotes.update(request));

    }

    @Test
    @DisplayName("La asignatura no existe.")
    public void onSubjectUpdateStudentWithoutSubject() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        // Mock
        Mockito.when(this.studentPort.getByIdOrCode(student.getId(), student.getCode())).thenReturn(student);
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(false);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(1)
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.subjectUpdateStudentNotes.update(request));

    }

    @Test
    @DisplayName("El estudiante no ha sido registrado a la asignatura.")
    public void onSubjectUpdateStudentWhenExists() {

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
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);
        Mockito.when(this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId())).thenReturn(null);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.subjectUpdateStudentNotes.update(request));

    }

    @Test
    @DisplayName("No se enviaron las notas que se desean actualizar.")
    public void onSubjectUpdateStudentWithoutNotes() {

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
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .id(0)
                .student(Student.builder().id(student.getId()).build())
                .subject(Subject.builder().id(subject.getId()).build())
                .build();
        Mockito.when(this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(null)
                .build();

        Assertions.assertThrows(FieldException.class, () -> this.subjectUpdateStudentNotes.update(request));

    }

    @Test
    @DisplayName("Las notas que se enviaron están fuera del limite.")
    public void onSubjectUpdateStudentLimitNotes() {

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
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .id(0)
                .student(Student.builder().id(student.getId()).build())
                .subject(Subject.builder().id(subject.getId()).build())
                .build();
        Mockito.when(this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(Arrays.asList(
                        new EntryNoteRequest(1, 1D),
                        new EntryNoteRequest(2, 5D),
                        new EntryNoteRequest(3, 4D),
                        new EntryNoteRequest(4, 1D)
                ))
                .build();

        Assertions.assertThrows(FieldException.class, () -> this.subjectUpdateStudentNotes.update(request));

    }

    @Test
    @DisplayName("Agregar notas nuevas.")
    public void onSubjectUpdateStudentNotes() {

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
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .id(0)
                .student(Student.builder().id(student.getId()).build())
                .subject(Subject.builder().id(subject.getId()).build())
                .build();
        Mockito.when(this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(Arrays.asList(
                        new EntryNoteRequest(3, 4D)
                ))
                .build();

        // Function
        this.subjectUpdateStudentNotes.update(request);

        ArgumentCaptor<Note> reply = ArgumentCaptor.forClass(Note.class);
        Mockito.verify(this.notePort, Mockito.times(request.getNotes().size())).save(reply.capture());

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

    @Test
    @DisplayName("Reemplazar notas antiguas")
    public void onSubjectUpdateStudentReplaceNotes() {

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
        Mockito.when(this.subjectPort.existsById(1)).thenReturn(true);

        SubjectStudent subjectStudent = SubjectStudent.builder()
                .id(0)
                .student(Student.builder().id(student.getId()).build())
                .subject(Subject.builder().id(subject.getId()).build())
                .build();
        Mockito.when(this.subjectStudentPort.getByStudentIdAndSubjectId(student.getId(), subject.getId())).thenReturn(subjectStudent);

        Note note = Note.builder()
                .id(1)
                .number(1)
                .note(1D)
                .subjectStudent(subjectStudent)
                .build();
        Mockito.when(this.notePort.findBySubjectStudentAndNumber(subjectStudent.getId(), note.getNumber())).thenReturn(note);

        SubjectUpdateStudentNotesRequest request = SubjectUpdateStudentNotesRequest.builder()
                .studentId(student.getId())
                .subjectId(1)
                .notes(List.of(new EntryNoteRequest(1, 2D)))
                .build();

        // Function
        this.subjectUpdateStudentNotes.update(request);

        ArgumentCaptor<Note> reply = ArgumentCaptor.forClass(Note.class);
        Mockito.verify(this.notePort, Mockito.times(1)).save(reply.capture());

        // Verify notes
        Assertions.assertEquals(reply.getValue().getSubjectStudent().getId(), subjectStudent.getId());
        Assertions.assertEquals(reply.getValue().getNumber(), 1);
        Assertions.assertEquals(reply.getValue().getNote(), 2D);

    }

}
