package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;

import java.util.List;

public interface SubjectCalculateFinalNote {

    SubjectWithFinalNoteModel calculateBySubjectAndNotes(Subject subject, List<Note> notes);

}
