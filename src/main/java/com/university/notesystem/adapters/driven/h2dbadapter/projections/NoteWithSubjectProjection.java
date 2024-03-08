package com.university.notesystem.adapters.driven.h2dbadapter.projections;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;

public interface NoteWithSubjectProjection {

    SubjectEntity getSubject();
    Integer getId();
    Integer getNumber();
    Double getNote();

}
