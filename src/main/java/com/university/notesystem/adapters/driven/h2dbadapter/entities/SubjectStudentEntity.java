package com.university.notesystem.adapters.driven.h2dbadapter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@NoArgsConstructor
@Setter
@AllArgsConstructor
@Builder
@Entity
@Table(name = "subject_students")
public class SubjectStudentEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private StudentEntity student;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private SubjectEntity subject;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<NoteEntity> notes;

}

