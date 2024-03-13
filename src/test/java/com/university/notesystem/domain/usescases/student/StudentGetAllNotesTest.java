package com.university.notesystem.domain.usescases.student;

import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.SimpleNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.student.StudentGetAllSubjectWithNotesImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@DisplayName("Obtener todas las notas por estudiante.")
@ExtendWith(MockitoExtension.class)
public class StudentGetAllNotesTest {

    @Mock
    private StudentPort studentPort;

    @Mock
    private SubjectStudentPort subjectStudentPort;

    @InjectMocks
    private StudentGetAllSubjectWithNotesImpl studentGetAllSubjectWithNotes;

    @Test
    @DisplayName("El estudiante no existe.")
    public void getAllNotesByIdOrCodeNotExists() {
        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(null);

        // Function
        Assertions.assertThrows(ResourceNotFoundException.class, () -> this.studentGetAllSubjectWithNotes.getAllByIdOrCode(1, 1));

    }

    @Test
    @DisplayName("El estudiante existe.")
    public void getAllNotesByIdOrCode() {

        Student student = Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build();

        Mockito.when(this.studentPort.getByIdOrCode(1, 1)).thenReturn(student);

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
        List<SubjectWithNotesModel> reply = this.studentGetAllSubjectWithNotes.getAllByIdOrCode(1, 1);

        Assertions.assertEquals(reply.size(), 2);

        SubjectWithNotesModel repSubject1 = reply.get(0);
        Assertions.assertEquals(repSubject1.getId(), subject1.getId());
        Assertions.assertEquals(repSubject1.getName(), subject1.getName());
        Assertions.assertEquals(repSubject1.getNotes().size(), 2);
        Assertions.assertEquals(repSubject1.getNotes().get(0).getId(), 1);
        Assertions.assertEquals(repSubject1.getNotes().get(0).getNumber(), 1);
        Assertions.assertEquals(repSubject1.getNotes().get(0).getNote(), 2D);
        Assertions.assertEquals(repSubject1.getNotes().get(1).getId(), 2);
        Assertions.assertEquals(repSubject1.getNotes().get(1).getNumber(), 2);
        Assertions.assertEquals(repSubject1.getNotes().get(1).getNote(), 3D);

        SubjectWithNotesModel repSubject2 = reply.get(1);
        Assertions.assertEquals(repSubject2.getId(), subject2.getId());
        Assertions.assertEquals(repSubject2.getName(), subject2.getName());
        Assertions.assertEquals(repSubject2.getNotes().size(), 2);
        Assertions.assertEquals(repSubject2.getNotes().get(0).getId(), 3);
        Assertions.assertEquals(repSubject2.getNotes().get(0).getNumber(), 1);
        Assertions.assertEquals(repSubject2.getNotes().get(0).getNote(), 1D);
        Assertions.assertEquals(repSubject2.getNotes().get(1).getId(), 4);
        Assertions.assertEquals(repSubject2.getNotes().get(1).getNumber(), 2);
        Assertions.assertEquals(repSubject2.getNotes().get(1).getNote(), 5D);

    }

}
