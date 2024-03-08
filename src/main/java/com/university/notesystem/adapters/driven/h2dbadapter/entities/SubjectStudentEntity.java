package com.university.notesystem.adapters.driven.h2dbadapter.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

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
    private Integer id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private StudentEntity student;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private SubjectEntity subject;

}

