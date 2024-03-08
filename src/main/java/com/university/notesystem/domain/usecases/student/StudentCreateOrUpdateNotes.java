package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.request.CreateOrUpdateNotesRequest;

public interface StudentCreateOrUpdateNotes {

    void createOrUpdate(int studentId, CreateOrUpdateNotesRequest data);

}
