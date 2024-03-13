package com.university.notesystem.adapters.httprest;

import com.university.notesystem.adapters.driven.h2dbadapter.repository.NoteRepository;
import com.university.notesystem.infrastructure.application.NoteSystemApplication;
import jakarta.annotation.Priority;
import jakarta.transaction.Transactional;
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
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest(classes = NoteSystemApplication.class)
@DisplayName("Controlador de notas finales de un estudiante.")
public class StudentsGetFinalNotesByIdControllerIntegrationTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @Priority(0)
    @DisplayName("Buscar notas finales por un estudiante que no existe.")
    public void onStudentGetAllNotesByIdWhenNotExists() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/students/1/final-notes"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extra.resource", Is.is("Student")));
    }

    @Test
    @Priority(1)
    @DisplayName("Buscar notas finales por un estudiante.")
    public void onStudentGetAllNotesById() throws Exception {

        this.mvc.perform(MockMvcRequestBuilders
                .post("/students")
                .contentType("application/json")
                .content("""
                            {
                                "id": 1,
                                "name": "Cristian",
                                "code": 1
                            }
                        """));

        this.mvc.perform(MockMvcRequestBuilders
                .post("/subjects")
                .contentType("application/json")
                .content("""
                            {
                                "id": 1,
                                "name": "Matemáticas"
                            }
                        """));

        this.mvc.perform(MockMvcRequestBuilders
                .post("/subjects")
                .contentType("application/json")
                .content("""
                            {
                                "id": 2,
                                "name": "Programación"
                            }
                        """));


        this.mvc.perform(MockMvcRequestBuilders
                .post("/subjects/1/students")
                .contentType("application/json")
                .content("""
                            {
                                "studentId": 1,
                                "notes": [
                                    { "number": 1, "value": 3 },
                                    { "number": 2, "value": 3 },
                                    { "number": 3, "value": 3 }
                                ]
                            }
                        """));

        this.mvc.perform(MockMvcRequestBuilders
                .post("/subjects/2/students")
                .contentType("application/json")
                .content("""
                            {
                                "studentId": 1,
                                "notes": [
                                    { "number": 1, "value": 1 },
                                    { "number": 2, "value": 2 }
                                ]
                            }
                        """));




        this.mvc.perform(MockMvcRequestBuilders
                        .get("/students/1/final-notes"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.OK.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].note", Is.is(3D)));

    }

}
