package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.mapper.SubjectWithFinalNoteDTOMapper;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StudentGetSubjectWithFinalNoteByIdImpl implements StudentGetSubjectWithFinalNoteById {

    private final StudentPort studentPort;
    private final SubjectStudentPort subjectStudentPort;

    @Override
    public List<SubjectWithFinalNoteModel> getAllByIdOrCode(Integer id, Integer code) {

        Student student = this.studentPort.getByIdOrCode(id, code);
        if (student == null) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id (" + id + ") o código (" + code + ").");
        }

        List<SubjectWithNotesModel> notes = this.subjectStudentPort.findAllSubjectWithNotesByStudent(student.getId());
        return notes
                .stream()
                .map(SubjectWithFinalNoteDTOMapper::mapToSubjectWithFinalNoteDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

}
