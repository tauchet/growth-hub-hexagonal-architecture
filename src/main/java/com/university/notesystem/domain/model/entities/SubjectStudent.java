package com.university.notesystem.domain.model.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class SubjectStudent {
    
    private int id;
    private Subject subject;
    private Student student;
    
}
