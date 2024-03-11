package com.university.notesystem.adapters.driver.httprest.controllers;

import com.university.notesystem.adapters.driver.httprest.dtos.CreateStudentDTO;
import com.university.notesystem.adapters.driver.httprest.responses.SuccessResponse;
import com.university.notesystem.domain.model.StudentWithAllFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.usecases.student.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class StudentsRestController {

    private final StudentGeneralManager studentGeneralManager;
    private final StudentGetAllSubjectWithNotes studentGetAllSubjectWithNotes;
    private final StudentGetSubjectWithFinalNote studentGetSubjectWithFinalNote;

    @GetMapping("students")
    public ResponseEntity<SuccessResponse<List<Student>>> onGetAll() {
        return SuccessResponse.create(HttpStatus.OK, this.studentGeneralManager.findAll());
    }

    @PostMapping("students")
    public ResponseEntity<SuccessResponse<Boolean>> onCreateStudent(@RequestBody CreateStudentDTO body) {
        this.studentGeneralManager.register(Student.builder()
                .id(body.getId())
                .code(body.getCode())
                .name(body.getName())
                .build());
        return SuccessResponse.create(HttpStatus.CREATED, true);
    }

    @DeleteMapping("students/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteStudent(@PathVariable int id) {
        this.studentGeneralManager.deleteById(id);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @GetMapping("students/{id}/notes")
    public ResponseEntity<SuccessResponse<List<SubjectWithNotesModel>>> onGetAllNotes(@PathVariable int id) {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetAllSubjectWithNotes.getAllByIdOrCode(id, id));
    }

    @GetMapping("students/{id}/final-notes")
    public ResponseEntity<SuccessResponse<List<SubjectWithFinalNoteModel>>> onGetAllFinalNotes(@PathVariable int id) {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetSubjectWithFinalNote.getAllByStudentIdOrCode(id, id));
    }

    @GetMapping("students/final-notes")
    public ResponseEntity<SuccessResponse<List<StudentWithAllFinalNoteModel>>> onGetAllFinalNotes() {
        return SuccessResponse.create(HttpStatus.OK, this.studentGetSubjectWithFinalNote.getAll());
    }

}
