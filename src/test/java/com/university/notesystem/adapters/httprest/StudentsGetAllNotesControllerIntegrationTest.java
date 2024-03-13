package com.university.notesystem.adapters.httprest;

import com.university.notesystem.infrastructure.application.NoteSystemApplication;
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

@SpringBootTest(classes = NoteSystemApplication.class)
@DisplayName("Controlador notas de estudiantes.")
@Transactional
public class StudentsGetAllNotesControllerIntegrationTest {

    private final String BASE_URL = "/students/1/notes";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Buscar notas por un estudiante que no existe")
    public void onStudentCreateWhenBodyIsFail() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extra.resource", Is.is("Student")));
    }

    @Test
    @DisplayName("Buscar notas por un estudiante")
    public void onStudentCreateWhenBodyIsFail2() throws Exception {

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Cristian",
                                                "code": 1
                                            }
                                        """));

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/subjects")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Matemáticas"
                                            }
                                        """));

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/subjects")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 2,
                                                "name": "Programación"
                                            }
                                        """));

        this.mvc
                .perform(
                        MockMvcRequestBuilders
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

        this.mvc
                .perform(
                        MockMvcRequestBuilders
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

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .get(BASE_URL))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.OK.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()", Is.is(2)));

    }


}
