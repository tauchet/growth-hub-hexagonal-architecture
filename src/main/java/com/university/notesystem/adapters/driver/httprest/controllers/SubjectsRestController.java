package com.university.notesystem.adapters.driver.httprest.controllers;

import com.university.notesystem.adapters.driver.httprest.dtos.CreateSubjectDTO;
import com.university.notesystem.adapters.driver.httprest.dtos.SubjectRegisterStudentDTO;
import com.university.notesystem.adapters.driver.httprest.dtos.SubjectUpdateStudentDTO;
import com.university.notesystem.adapters.driver.httprest.responses.SuccessResponse;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.model.request.SubjectUpdateStudentNotesRequest;
import com.university.notesystem.domain.usecases.subject.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SubjectsRestController {

    private final SubjectGeneralManager subjectGeneralManager;
    private final SubjectRegisterStudent subjectRegisterStudent;
    private final SubjectUpdateStudentNotes subjectUpdateStudentNotes;

    @GetMapping("subjects")
    public ResponseEntity<SuccessResponse<List<Subject>>> onGetAll() {
        return SuccessResponse.create(HttpStatus.OK, this.subjectGeneralManager.findAll());
    }

    @PostMapping("subjects")
    public ResponseEntity<SuccessResponse<Boolean>> onCreateSubject(@RequestBody @Valid CreateSubjectDTO body) {
        this.subjectGeneralManager.register(Subject.builder()
                .id(body.getId())
                .name(body.getName())
                .build());
        return SuccessResponse.create(HttpStatus.CREATED, true);
    }

    @DeleteMapping("subjects/{id}")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteSubject(@PathVariable int id) {
        this.subjectGeneralManager.deleteById(id);
        return SuccessResponse.create(HttpStatus.OK, true);
    }

    @PostMapping("subjects/{id}/students")
    public ResponseEntity<SuccessResponse<Boolean>> onDeleteSubject(@PathVariable int id,
                                                                    @RequestBody @Valid SubjectRegisterStudentDTO request) {
        this.subjectRegisterStudent.register(
                SubjectRegisterStudentRequest
                        .builder()
                        .studentId(request.getStudentId())
                        .subjectId(id)
                        .notes(request.getNotes())
                        .build()
        );
        return SuccessResponse.create(HttpStatus.CREATED, true);
    }

    @PutMapping("subjects/{id}/update-notes")
    public ResponseEntity<SuccessResponse<Boolean>> onUpdateNotes(@PathVariable int id,
                                                                  @RequestBody @Valid SubjectUpdateStudentDTO request) {
        this.subjectUpdateStudentNotes.update(
                SubjectUpdateStudentNotesRequest
                        .builder()
                        .studentId(request.getStudentId())
                        .subjectId(id)
                        .notes(request.getNotes())
                        .build()
        );
        return SuccessResponse.create(HttpStatus.OK, true);
    }

}
