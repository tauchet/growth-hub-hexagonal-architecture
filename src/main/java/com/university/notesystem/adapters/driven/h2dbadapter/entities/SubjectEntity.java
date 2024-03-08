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
@Table(name = "subjects")
public class SubjectEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List<SubjectStudentEntity> students;
    
}
