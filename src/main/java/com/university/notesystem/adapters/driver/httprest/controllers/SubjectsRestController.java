package com.university.notesystem.adapters.driver.httprest.controllers;

import com.university.notesystem.adapters.driver.httprest.dtos.CreateSubjectDTO;
import com.university.notesystem.adapters.driver.httprest.responses.SuccessResponse;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.usecases.subject.SubjectAll;
import com.university.notesystem.domain.usecases.subject.SubjectDelete;
import com.university.notesystem.domain.usecases.subject.SubjectRegister;
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

}
