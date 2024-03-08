package com.university.notesystem.adapters.driver.httprest.controllers;

import com.university.notesystem.adapters.driver.httprest.dtos.CreateStudentDTO;
import com.university.notesystem.adapters.driver.httprest.responses.SuccessResponse;
import com.university.notesystem.domain.model.dtos.StudentWithAllFinalNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithFinalNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.request.CreateOrUpdateNotesRequest;
import com.university.notesystem.domain.usecases.student.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentsRestController {

    private final StudentRegister studentRegister;
    private final StudentDelete studentDelete;
    private final StudentAll studentAll;
    private final StudentCreateOrUpdateNotes studentCreateOrUpdateNotes;
    private final StudentGetAllSubjectWithNotes studentGetAllSubjectWithNotes;
    private final StudentGetSubjectWithFinalNoteById studentGetSubjectWithFinalNoteById;
    private final StudentGetSubjectWithFinalNoteByAll studentGetSubjectWithFinalNoteByAll;

    @GetMapping("students")
    public ResponseEntity<SuccessResponse<List<Student>>> onGetAll() {
        return SuccessResponse.create(HttpStatus.OK, this.studentAll.findAll());
    }

    @PostMapping("students")
    public ResponseEntity<SuccessResponse<Boolean>> onCreateStudent(@RequestBody CreateStudentDTO body) {
        this.studentRegister.register(Student.builder()
                .id(body.getId())
                .code(body.getCode())
                .name(body.getName())
                .build());
        return SuccessResponse.create(HttpStatus.CREATED, true);
    }

    @DeleteMapping("students/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteStudent(@PathVariable int id) {
        this.studentDelete.deleteById(id);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @GetMapping("students/{id}/notes")
    public ResponseEntity<SuccessResponse<List<SubjectWithNotesDTO>>> onGetAllNotes(@PathVariable int id) {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetAllSubjectWithNotes.getAllByIdOrCode(id, id));
    }

    @PutMapping("students/{id}/notes")
    public ResponseEntity<SuccessResponse<Boolean>> onCreateOrUpdate(@PathVariable int id, @RequestBody CreateOrUpdateNotesRequest body) {
        this.studentCreateOrUpdateNotes.createOrUpdate(id, body);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @GetMapping("students/{id}/final-notes")
    public ResponseEntity<SuccessResponse<List<SubjectWithFinalNoteDTO>>> onGetAllFinalNotes(@PathVariable int id) {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetSubjectWithFinalNoteById.getAllByIdOrCode(id, id));
    }

    @GetMapping("students/final-notes")
    public ResponseEntity<SuccessResponse<List<StudentWithAllFinalNoteDTO>>> onGetAllFinalNotes() {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetSubjectWithFinalNoteByAll.getAll());
    }

}
