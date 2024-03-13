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
@DisplayName("Controlador de asignaturas.")
@Transactional
public class SubjectsGeneralControllerIntegrationTest {

    private final String BASE_URL = "/subjects";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Crear una asignatura con datos erroneos.")
    public void onStudentCreateWhenBodyIsFail() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "2": "Matemáticas"
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("FIELD")));
    }

    @Test
    @DisplayName("Crear una asignatura.")
    public void onStudentCreateSuccess() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Matemáticas"
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.CREATED.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)));
    }

    @Test
    @DisplayName("Crear una asignatura cuando ya existe otro con la misma id.")
    public void onStudentCreateSuccessFail() throws Exception {

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
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
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Programación"
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_ALREADY_EXISTS")));

    }

    @Test
    @DisplayName("Obtener todas las asignaturas cuando es vacío.")
    public void onGetAllSubjectsWhenIsEmpty() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .get(BASE_URL)
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.OK.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()", Is.is(0)));
    }

    @Test
    @DisplayName("Obtener todas las asignaturas.")
    public void onGetAllStudents() throws Exception {

        // Create student
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
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
                                .get(BASE_URL)
                                .contentType("application/json")
                )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.OK.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.length()", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].id", Is.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].name", Is.is("Matemáticas")));
    }



}
