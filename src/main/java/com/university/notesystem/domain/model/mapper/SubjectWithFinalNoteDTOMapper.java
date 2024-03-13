package com.university.notesystem.domain.model.mapper;

import com.university.notesystem.domain.model.SimpleNoteModel;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SubjectWithFinalNoteDTOMapper {

    public static SubjectWithFinalNoteModel mapToSubjectWithFinalNoteDTO(Subject subject, List<Note> notes) {

        if (notes == null || notes.size() < 3) {
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

    public static SubjectWithFinalNoteModel mapToSubjectWithFinalNoteDTO(SubjectWithNotesModel dto) {

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

}
