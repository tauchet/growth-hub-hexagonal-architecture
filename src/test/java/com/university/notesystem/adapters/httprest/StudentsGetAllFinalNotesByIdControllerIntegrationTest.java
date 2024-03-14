package com.university.notesystem.adapters.httprest;

import com.university.notesystem.adapters.driven.h2dbadapter.repository.NoteRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.StudentRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectStudentRepository;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.request.EntryNoteRequest;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.usecases.student.StudentGeneralManager;
import com.university.notesystem.domain.usecases.subject.SubjectGeneralManager;
import com.university.notesystem.domain.usecases.subject.SubjectRegisterStudent;
import com.university.notesystem.infrastructure.application.NoteSystemApplication;
import jakarta.annotation.Priority;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = NoteSystemApplication.class)
@DisplayName("Controlador de todas las notas finales.")
public class StudentsGetAllFinalNotesByIdControllerIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private StudentGeneralManager studentGeneralManager;

    @Autowired
    private SubjectGeneralManager subjectGeneralManager;

    @Autowired
    private SubjectRegisterStudent subjectRegisterStudent;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectStudentRepository subjectStudentRepository;

    @Autowired
    private NoteRepository noteRepository;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private void clear() {
        this.subjectStudentRepository.deleteAll();
        this.subjectRepository.deleteAll();
        this.studentRepository.deleteAll();
        this.noteRepository.deleteAll();
    }

    @Test
    @DisplayName("Buscar notas finales por todos los estudiantes.")
    public void onStudentGetAllFinalNotes() throws Exception {

        clear();

        this.studentGeneralManager.register(Student.builder()
                .id(1)
                .code(1)
                .name("Cristian")
                .build());

        this.studentGeneralManager.register(Student.builder()
                .id(2)
                .code(2)
                .name("Pepito")
                .build());

        this.subjectGeneralManager.register(Subject.builder()
                .id(1)
                .name("Matemáticas")
                .build());

        this.subjectGeneralManager.register(Subject.builder()
                .id(2)
                .name("Programación")
                .build());

        this.subjectRegisterStudent.register(
                new SubjectRegisterStudentRequest(
                        1,
                        1,
                        List.of(
                                new EntryNoteRequest(1, 3D),
                                new EntryNoteRequest(2, 3D),
                                new EntryNoteRequest(3, 3D)
                        )
                )
        );

        this.subjectRegisterStudent.register(
                new SubjectRegisterStudentRequest(
                        1,
                        2,
                        List.of(
                                new EntryNoteRequest(1, 1D),
                                new EntryNoteRequest(2, 2D)
                        )
                )
        );

        this.subjectRegisterStudent.register(
                new SubjectRegisterStudentRequest(
                        2,
                        2,
                        List.of(
                                new EntryNoteRequest(1, 2D),
                                new EntryNoteRequest(2, 2D),
                                new EntryNoteRequest(3, 2D)
                        )
                )
        );

        this.mvc.perform(MockMvcRequestBuilders
                        .get("/students/final-notes"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.OK.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].notes.[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].notes.[0].note", Is.is(3D)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[1].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[1].notes.[0].id", Is.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[1].notes.[0].note", Is.is(2D)));

        clear();

    }

}
