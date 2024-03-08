package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.model.request.NoteEntryRequest;
import com.university.notesystem.domain.model.request.SubjectUpdateStudentNotesRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubjectUpdateStudentNotesImpl implements SubjectUpdateStudentNotes {

    private final StudentPort studentPort;
    private final SubjectPort subjectPort;
    private final SubjectStudentPort subjectStudentPort;
    private final NotePort notePort;

    @Override
    public void update(SubjectUpdateStudentNotesRequest data) {

        Student student = this.studentPort.getByIdOrCode(data.getStudentId(), data.getStudentId());
        if (student == null) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id o código (" + data.getStudentId() + ").");
        }

        if (!this.subjectPort.existsById(data.getSubjectId())) {
            throw new ResourceNotFoundException("Subject", "No se ha encontrado la asignatura por el id (" + data.getSubjectId() + ").");
        }

        SubjectStudent register = this.subjectStudentPort.getByStudentIdOrCodeAndSubjectId(student.getId(), data.getSubjectId());
        if (register == null) {
            throw new ResourceNotFoundException("SubjectStudent", "No se ha encontrado el registro a la asignatura indicada.");
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

            Note currentNote = this.notePort.findBySubjectStudentAndNumber(register.getId(), note.getNumber());
            if (currentNote == null) {
                currentNote = Note.builder()
                        .subjectStudent(register)
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
