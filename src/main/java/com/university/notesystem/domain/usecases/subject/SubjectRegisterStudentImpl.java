package com.university.notesystem.domain.usecases.subject;

import com.university.notesystem.domain.exceptions.FieldException;
import com.university.notesystem.domain.exceptions.ResourceAlreadyExistsException;
import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.entities.SubjectStudent;
import com.university.notesystem.domain.model.request.EntryNoteRequest;
import com.university.notesystem.domain.model.request.SubjectRegisterStudentRequest;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.UseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubjectRegisterStudentImpl implements SubjectRegisterStudent, UseCase {

    private final StudentPort studentPort;
    private final SubjectPort subjectPort;
    private final SubjectStudentPort subjectStudentPort;
    private final NotePort notePort;

    @Override
    public void register(SubjectRegisterStudentRequest data) {

        Student student = this.studentPort.getByIdOrCode(data.getStudentId(), data.getStudentId());
        if (student == null) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id o código (" + data.getStudentId() + ").");
        }

        if (!this.subjectPort.existsById(data.getSubjectId())) {
            throw new ResourceNotFoundException("Subject", "No se ha encontrado la asignatura por el id (" + data.getSubjectId() + ").");
        }

        if (this.subjectStudentPort.existsByStudentAndSubject(student.getId(), data.getSubjectId())) {
            throw new ResourceAlreadyExistsException("id", "¡El estudiante ya se encuentra registrado!");
        }

        if (data.getNotes() != null) {
            for (int i = 0; i < data.getNotes().size(); ++i) {
                EntryNoteRequest note = data.getNotes().get(i);
                if (note.getNumber() < 0 || note.getNumber() > 3) {
                    throw new FieldException("notes." + i, "¡El número de la nota debe estar entre 1 y 3!");
                }
            }
        }

        SubjectStudent register = this.subjectStudentPort.save(
                SubjectStudent.builder()
                        .student(Student.builder().id(data.getStudentId()).build())
                        .subject(Subject.builder().id(data.getSubjectId()).build())
                        .build());

        if (data.getNotes() == null || data.getNotes().isEmpty()) {
            return;
        }

        for (EntryNoteRequest note: data.getNotes()) {
            this.notePort.save(Note.builder()
                    .subjectStudent(SubjectStudent.builder().id(register.getId()).build())
                    .number(note.getNumber())
                    .note(note.getValue())
                    .build());
        }

    }

}
