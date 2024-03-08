package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;

public interface SubjectRegisterStudent {

    void register(SubjectRegisterStudentRequest request);

}
