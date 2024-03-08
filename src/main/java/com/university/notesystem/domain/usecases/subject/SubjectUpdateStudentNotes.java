package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.request.SubjectUpdateStudentNotesRequest;

public interface SubjectUpdateStudentNotes {

    void update(SubjectUpdateStudentNotesRequest request);

}
