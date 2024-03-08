package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StudentGetAllSubjectWithNotesImpl implements StudentGetAllSubjectWithNotes {

    private final StudentPort studentPort;
    private final NotePort notePort;

    @Override
    public List<SubjectWithNotesDTO> getAllByIdOrCode(Integer id, Integer code) {

        Student student = this.studentPort.getByIdOrCode(id, code);
        if (student == null) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id (" + id + ") o código (" + code + ").");
        }

        return this.notePort.findAllSubjectWithNotesByStudent(student.getId());
    }

}
