package com.university.notesystem.adapters.httprest;

import com.university.notesystem.infrastructure.application.NoteSystemApplication;
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

@SpringBootTest(classes = NoteSystemApplication.class)
@DisplayName("Controlador general de estudiantes.")
@Transactional
public class StudentsGeneralControllerIntegrationTest {

    private final String BASE_URL = "/students";

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    @DisplayName("Crear un estudiante con datos erroneos.")
    public void onStudentCreateWhenBodyIsFail() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "2": "Cristian",
                                                "test": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("FIELD")));
    }

    @Test
    @DisplayName("Crear un estudiante.")
    public void onStudentCreateSuccess() throws Exception {
        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Cristian",
                                                "code": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.CREATED.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.CREATED.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.success", Is.is(true)));
    }

    @Test
    @DisplayName("Crear un estudiante cuando ya existe otro con la id o código.")
    public void onStudentCreateFailWithIdOrCodeUsed() throws Exception {

        this.mvc
                .perform(
                        MockMvcRequestBuilders
                                .post(BASE_URL)
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
                                .post(BASE_URL)
                                .contentType("application/json")
                                .content("""
                                            {
                                                "id": 1,
                                                "name": "Camilo",
                                                "code": 1
                                            }
                                        """))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.UNPROCESSABLE_ENTITY.value()))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Is.is(HttpStatus.UNPROCESSABLE_ENTITY.value())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.error", Is.is("RESOURCE_ALREADY_EXISTS")));

    }

    @Test
    @DisplayName("Obtener todos los estudiantes cuando es vacío.")
    public void onGetAllStudentsWhenIsEmpty() throws Exception {
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
    @DisplayName("Obtener todos los estudiantes.")
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
                                                "name": "Cristian",
                                                "code": 1
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.success.[0].name", Is.is("Cristian")));
    }

}
