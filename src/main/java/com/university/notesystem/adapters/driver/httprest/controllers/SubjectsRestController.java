package com.university.notesystem.adapters.driver.httprest.controllers;

import com.university.notesystem.adapters.driver.httprest.dtos.CreateSubjectDTO;
import com.university.notesystem.adapters.driver.httprest.responses.SuccessResponse;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.model.request.SubjectUpdateStudentNotesRequest;
import com.university.notesystem.domain.usecases.subject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SubjectsRestController {

    private final SubjectRegister subjectRegister;
    private final SubjectAll subjectAll;
    private final SubjectDelete subjectDelete;
    private final SubjectRegisterStudent subjectRegisterStudent;
    private final SubjectUpdateStudentNotes subjectUpdateStudentNotes;

    @GetMapping("subjects")
    public ResponseEntity<SuccessResponse<List<Subject>>> onGetAll() {
        return SuccessResponse.create(HttpStatus.OK, this.subjectAll.findAll());
    }

    @PostMapping("subjects")
    public ResponseEntity<SuccessResponse<Boolean>> onCreateSubject(@RequestBody CreateSubjectDTO body) {
        this.subjectRegister.register(Subject.builder()
                .id(body.getId())
                .name(body.getName())
                .build());
        return SuccessResponse.create(HttpStatus.CREATED, true);
    }

    @DeleteMapping("subjects/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteSubject(@PathVariable int id) {
        this.subjectDelete.deleteById(id);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @PostMapping("subjects/{id}/students")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteSubject(@PathVariable int id,
                                                                    @RequestBody SubjectRegisterStudentRequest request) {
        request.setSubjectId(id);
        this.subjectRegisterStudent.register(request);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @PostMapping("subjects/{id}/update-notes")
    public ResponseEntity<SuccessResponse<Boolean>> onUpdateNotes(@PathVariable int id,
                                                                  @RequestBody SubjectUpdateStudentNotesRequest request) {
        request.setSubjectId(id);
        this.subjectUpdateStudentNotes.update(request);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

}
