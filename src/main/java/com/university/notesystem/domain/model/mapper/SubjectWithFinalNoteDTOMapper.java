package com.university.notesystem.domain.model.mapper;

import com.university.notesystem.domain.model.dtos.SimpleNoteDTO;
import com.university.notesystem.domain.model.SubjectWithFinalNote;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Subject;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class SubjectWithFinalNoteDTOMapper {

    public static SubjectWithFinalNote mapToSubjectWithFinalNoteDTO(Subject subject, List<Note> notes) {

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

    public static SubjectWithFinalNote mapToSubjectWithFinalNoteDTO(SubjectWithNotesDTO dto) {

        if (dto.getNotes().size() < 3) {
            return null;
        }

        BigDecimal decimal = new BigDecimal(0);
        for (SimpleNoteDTO note: dto.getNotes()) {
            decimal = decimal.add(BigDecimal.valueOf(note.getNote()));
        }
        decimal = decimal.divide(new BigDecimal(dto.getNotes().size()), new MathContext(5));

        return new SubjectWithFinalNote(
                dto.getId(),
                dto.getName(),
                decimal.doubleValue()
        );

    }

}
