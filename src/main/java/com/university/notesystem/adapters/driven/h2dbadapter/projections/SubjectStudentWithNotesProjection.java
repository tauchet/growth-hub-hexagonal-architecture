package com.university.notesystem.adapters.driven.h2dbadapter.projections;

import com.university.notesystem.adapters.driven.h2dbadapter.entities.SubjectEntity;

import java.util.List;

public interface SubjectStudentWithNotesProjection {

    SubjectEntity getSubject();
    List<SimpleNoteProjection> getNotes();

}
