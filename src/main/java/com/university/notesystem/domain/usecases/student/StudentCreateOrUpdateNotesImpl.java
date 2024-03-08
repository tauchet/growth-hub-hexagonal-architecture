package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.request.CreateOrUpdateNotesRequest;
import com.university.notesystem.domain.model.request.NoteEntryRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StudentCreateOrUpdateNotesImpl implements StudentCreateOrUpdateNotes {

    private final StudentPort studentPort;
    private final SubjectPort subjectPort;
    private final NotePort notePort;

    @Override
    public void createOrUpdate(int studentId, CreateOrUpdateNotesRequest data) {

        if (!this.studentPort.existsByIdOrCode(studentId, studentId)) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id (" + studentId + ") o código (" + studentId + ").");
        }

        if (!this.subjectPort.existsById(data.getSubjectId())) {
            throw new ResourceNotFoundException("Subject", "No se ha encontrado la asignatura por el id (" + data.getSubjectId() + ").");
        }

        if (data.getNotes() == null || data.getNotes().isEmpty()) {
            throw new FieldException("notes", "¡Las notas no pueden estar vacías!");
        }

        if (data.getNotes().size() > 3) {
            throw new FieldException("notes", "¡No pueden existir más de 3 notas!");
        }

        for (int i = 0; i < data.getNotes().size(); ++i) {

            NoteEntryRequest note = data.getNotes().get(i);
            if (note.getNumber() < 0 || note.getNumber() > 3) {
                throw new FieldException("notes." + i, "¡El número de la nota debe estar entre 1 y 3!");
            }

            Note currentNote = this.notePort.findByStudentAndSubjectAndNumber(studentId, data.getSubjectId(), note.getNumber());
            if (currentNote == null) {
                currentNote = Note.builder()
                        .student(Student.builder().id(studentId).build())
                        .subject(Subject.builder().id(data.getSubjectId()).build())
                        .number(note.getNumber())
                        .note(note.getValue())
                        .build();
            } else {
                currentNote.setNote(note.getValue());
            }

            this.notePort.save(currentNote);

        }

    }

}
