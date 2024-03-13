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
@DisplayName("Controlador de registrar un estudiante en la asignatura.")
@Transactional
public class SubjectsRegisterStudentControllerIntegrationTest {


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Registrar estudiante con valores erroneos.")
    public void onStudentCreateWhenBodyIsFail() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/subjects/1/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "dsdd": "dsds"
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("FIELD")));
    }

    @Test
    @DisplayName("Registrar estudiante con valores correctos cuando no existe el estudiante.")
    public void onStudentCreateWhenBodyIsFail2() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post( "/subjects/1/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "studentId": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extra.resource", Is.is("Student")));
    }

    @Test
    @DisplayName("Registrar estudiante con valores correctos cuando no existe la asignatura.")
    public void onStudentCreateWhenBodyIsFail3() throws Exception {

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
                                .post("/subjects/1/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "studentId": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.NOT_FOUND.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extra.resource", Is.is("Subject")));
    }

    @Test
    @DisplayName("Registrar estudiante sin notas.")
    public void onStudentCreateWhenBodyIsFail4() throws Exception {

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
                                .post("/subjects/1/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "studentId": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.CREATED.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)));

    }

    @Test
    @DisplayName("Registrar estudiante con notas fuera de limite.")
    public void onStudentCreateWhenBodyIsFail5() throws Exception {

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
                                .post("/subjects/1/students")
                                .contentType("application/json")
                                .content("""
                                            {
                                                "studentId": 1,
                                                "notes": [
                                                    { "number": 5, "value": 3 }
                                                ]
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("FIELD")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.extra.field", Is.is("notes.0")));

    }

    @Test
    @DisplayName("Registrar estudiante con notas.")
    public void onStudentCreateWhenBodyIsFail6() throws Exception {

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
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.CREATED.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)));

    }

}
