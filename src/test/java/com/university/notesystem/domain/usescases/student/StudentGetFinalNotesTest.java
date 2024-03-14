package com.university.notesystem.domain.usescases.student;

import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.*;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.student.StudentGetSubjectWithFinalNoteImpl;
import com.university.notesystem.domain.usecases.subject.SubjectCalculateFinalNote;
import com.university.notesystem.domain.usecases.subject.SubjectCalculateFinalNoteImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@DisplayName("Obtener las notas finales por estudiante.")
@ExtendWith(MockitoExtension.class)
public class StudentGetFinalNotesTest {

    @Mock
    private StudentPort studentPort;

    @Mock
    private SubjectStudentPort subjectStudentPort;

    @Spy
    private SubjectCalculateFinalNoteImpl subjectCalculateFinalNote;

    @InjectMocks
    private StudentGetSubjectWithFinalNoteImpl studentGetSubjectWithFinalNote;


    @Test
    @DisplayName("El estudiante no existe.")
    public void getAllFinalNotesByIdOrCodeNotExists() {
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(null);

        // Function
        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.studentGetSubjectWithFinalNote.getAllByStudentIdOrCode(1, 1));

    }

    @Test
    @DisplayName("Notas finales cuando no existen suficientes notas.")
    public void getAllFinalNotesByIdOrCodeWhenNotesNotFound() {

        // Estudiante
        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(student);

        // Notas
        Subject subject1 = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();
        Subject subject2 = Subject.builder()
                .id(2)
                .name("Programación")
                .build();
        Mockito.when(this.subjectStudentPort.findAllSubjectWithNotesByStudent(1))
                .thenReturn(Arrays.asList(
                        SubjectWithNotesModel.builder()
                                .id(subject1.getId())
                                .name(subject1.getName())
                                .notes(Arrays.asList(
                                        SimpleNoteModel.builder().id(1).number(1).note(2D).build(),
                                        SimpleNoteModel.builder().id(2).number(2).note(3D).build()
                                ))
                                .build(),
                        SubjectWithNotesModel.builder()
                                .id(subject2.getId())
                                .name(subject2.getName())
                                .notes(Arrays.asList(
                                        SimpleNoteModel.builder().id(3).number(1).note(1D).build(),
                                        SimpleNoteModel.builder().id(4).number(2).note(5D).build()
                                ))
                                .build()
                ));

        // Function
        List<SubjectWithFinalNoteModel> reply = this.studentGetSubjectWithFinalNote.getAllByStudentIdOrCode(1, 1);
        Assertions.assertEquals(0, reply.size());

    }

    @Test
    @DisplayName("Notas finales posibles por asignatura.")
    public void getAllFinalNotesByIdOrCodeComplete() {

        // Estudiante
        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(student);

        // Notas
        Subject subject1 = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();
        Subject subject2 = Subject.builder()
                .id(2)
                .name("Programación")
                .build();
        Mockito.when(this.subjectStudentPort.findAllSubjectWithNotesByStudent(1))
                .thenReturn(Arrays.asList(
                        SubjectWithNotesModel.builder()
                                .id(subject1.getId())
                                .name(subject1.getName())
                                .notes(Arrays.asList(
                                        SimpleNoteModel.builder().id(1).number(1).note(2D).build(),
                                        SimpleNoteModel.builder().id(2).number(2).note(3D).build(),
                                        SimpleNoteModel.builder().id(5).number(3).note(4D).build()
                                ))
                                .build(),
                        SubjectWithNotesModel.builder()
                                .id(subject2.getId())
                                .name(subject2.getName())
                                .notes(Arrays.asList(
                                        SimpleNoteModel.builder().id(3).number(1).note(1D).build(),
                                        SimpleNoteModel.builder().id(4).number(2).note(5D).build()
                                ))
                                .build()
                ));

        // Function
        List<SubjectWithFinalNoteModel> reply = this.studentGetSubjectWithFinalNote.getAllByStudentIdOrCode(1, 1);
        Assertions.assertEquals(1, reply.size());

        // Subject 1: 2 + 3 + 4 / 3 = 3
        // Subject 2: 1 + 5 = null (se filtra)

        SubjectWithFinalNoteModel entry = reply.get(0);
        Assertions.assertEquals(entry.getId(), subject1.getId());
        Assertions.assertEquals(entry.getName(), subject1.getName());
        Assertions.assertEquals(entry.getNote(), 3D);

    }

    @Test
    @DisplayName("Todas las notas finales por cada estudiante posibles por asignatura.")
    public void getAllFinalNotesByAllComplete() {

        // Estudiante
        Student student1 = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        Student student2 = Student.builder()
                .id(2)
                .code(2)
                .name("Camilo")
                .build();

        Student student3 = Student.builder()
                .id(3)
                .code(3)
                .name("Pepito")
                .build();

        // Notas
        Subject subject1 = Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build();
        Subject subject2 = Subject.builder()
                .id(2)
                .name("Programación")
                .build();

        Mockito.when(this.subjectStudentPort.findAllSubjectWithNotes())
                .thenReturn(Arrays.asList(
                        SubjectStudentWithNotesModel
                                .builder()
                                .id(1)
                                .student(student1)
                                .subject(subject1)
                                .notes(Arrays.asList(
                                        Note.builder().id(1).number(1).note(2D).build(),
                                        Note.builder().id(2).number(2).note(3D).build(),
                                        Note.builder().id(5).number(3).note(4D).build()
                                ))
                                .build(),
                        SubjectStudentWithNotesModel
                                .builder()
                                .id(2)
                                .student(student1)
                                .subject(subject2)
                                .notes(Arrays.asList(
                                        Note.builder().id(3).number(1).note(1D).build(),
                                        Note.builder().id(4).number(2).note(5D).build()
                                ))
                                .build(),
                        SubjectStudentWithNotesModel
                                .builder()
                                .id(3)
                                .student(student2)
                                .subject(subject2)
                                .notes(Arrays.asList(
                                        Note.builder().id(6).number(1).note(2D).build(),
                                        Note.builder().id(7).number(2).note(3D).build()
                                ))
                                .build(),
                        SubjectStudentWithNotesModel
                                .builder()
                                .id(4)
                                .student(student2)
                                .subject(subject2)
                                .notes(Arrays.asList(
                                        Note.builder().id(8).number(1).note(5D).build(),
                                        Note.builder().id(9).number(2).note(2D).build(),
                                        Note.builder().id(10).number(3).note(5D).build()
                                ))
                                .build(),
                        SubjectStudentWithNotesModel
                                .builder()
                                .id(5)
                                .student(student3)
                                .subject(subject2)
                                .notes(Collections.emptyList())
                                .build()
                ));

        // Function
        List<StudentWithAllFinalNoteModel> reply = this.studentGetSubjectWithFinalNote.getAll();
        Assertions.assertEquals(reply.size(), 3);



        // Cristian -> Matemáticas 2 + 3 + 4 = 9 / 3 = 3
        Assertions.assertEquals(reply.get(0).getNotes().size(), 1);
        Assertions.assertEquals(reply.get(0).getNotes().get(0).getId(), subject1.getId());
        Assertions.assertEquals(reply.get(0).getNotes().get(0).getName(), subject1.getName());
        Assertions.assertEquals(reply.get(0).getNotes().get(0).getNote(), 3D);

        // Camilo -> Programación 5 + 2 + 5 = 12 / 3 = 4
        Assertions.assertEquals(reply.get(1).getNotes().size(), 1);
        Assertions.assertEquals(reply.get(1).getNotes().get(0).getId(), subject2.getId());
        Assertions.assertEquals(reply.get(1).getNotes().get(0).getName(), subject2.getName());
        Assertions.assertEquals(reply.get(1).getNotes().get(0).getNote(), 4D);

        // Pepito
        Assertions.assertEquals(reply.get(2).getNotes().size(), 0);

    }

}
