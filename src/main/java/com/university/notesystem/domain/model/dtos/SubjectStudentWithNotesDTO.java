package com.university.notesystem.domain.model.dtos;

import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectStudentWithNotesDTO {

    private int id;
    private Subject subject;
    private Student student;
    private List<Note> notes;

}
