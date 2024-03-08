package com.university.notesystem.adapters.driven.h2dbadapter.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notes")
public class NoteEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private SubjectStudentEntity subjectStudent;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ColumnDefault(value = "0")
    private Double note;

    
}
