package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.SubjectWithFinalNote;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;

import java.util.List;

public interface SubjectCalculateFinalNote {

    SubjectWithFinalNote calculateBySubjectAndNotes(Subject subject, List<Note> notes);

}
