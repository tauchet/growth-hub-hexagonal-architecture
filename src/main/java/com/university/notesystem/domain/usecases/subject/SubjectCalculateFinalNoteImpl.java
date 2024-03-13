package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.model.SimpleNoteModel;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.usecases.UseCase;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SubjectCalculateFinalNoteImpl implements SubjectCalculateFinalNote, UseCase {

    public SubjectWithFinalNoteModel calculate(SubjectWithNotesModel dto) {

        if (dto.getNotes() == null || dto.getNotes().size() < 3) {
            return null;
        }

        BigDecimal decimal = new BigDecimal(0);
        for (SimpleNoteModel note: dto.getNotes()) {
            decimal = decimal.add(BigDecimal.valueOf(note.getNote()));
        }
        decimal = decimal.divide(new BigDecimal(dto.getNotes().size()), new MathContext(5));

        return new SubjectWithFinalNoteModel(
                dto.getId(),
                dto.getName(),
                decimal.doubleValue()
        );

    }

    @Override
    public SubjectWithFinalNoteModel calculate(Subject subject, List<Note> notes) {

        if (notes.size() < 3) {
            return null;
        }

        BigDecimal decimal = new BigDecimal(0);
        for (Note note: notes) {
            decimal = decimal.add(BigDecimal.valueOf(note.getNote()));
        }
        decimal = decimal.divide(new BigDecimal(notes.size()), new MathContext(5));

        return new SubjectWithFinalNoteModel(
                subject.getId(),
                subject.getName(),
                decimal.doubleValue()
        );

    }

}
