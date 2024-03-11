package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.SubjectWithFinalNote;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SubjectCalculateFinalNoteImpl implements SubjectCalculateFinalNote {

    @Override
    public SubjectWithFinalNote calculateBySubjectAndNotes(Subject subject, List<Note> notes) {

        if (notes.size() < 3) {
            return null;
        }

        BigDecimal decimal = new BigDecimal(0);
        for (Note note: notes) {
            decimal = decimal.add(BigDecimal.valueOf(note.getNote()));
        }
        decimal = decimal.divide(new BigDecimal(notes.size()), new MathContext(5));

        return new SubjectWithFinalNote(
                subject.getId(),
                subject.getName(),
                decimal.doubleValue()
        );

    }

}
