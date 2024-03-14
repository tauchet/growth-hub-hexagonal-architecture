package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.exceptions.ResourceNotFoundException;
import com.university.notesystem.domain.model.StudentWithAllFinalNoteModel;
import com.university.notesystem.domain.model.SubjectStudentWithNotesModel;
import com.university.notesystem.domain.model.SubjectWithFinalNoteModel;
import com.university.notesystem.domain.model.SubjectWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.subject.SubjectCalculateFinalNote;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class StudentGetSubjectWithFinalNoteImpl implements StudentGetSubjectWithFinalNote {

    private final StudentPort studentPort;
    private final SubjectStudentPort subjectStudentPort;
    private final SubjectCalculateFinalNote calculateFinalNote;

    @Override
    public List<SubjectWithFinalNoteModel> getAllByStudentIdOrCode(Integer id, Integer code) {

        Student student = this.studentPort.getByIdOrCode(id, code);
        if (student == null) {
            throw new ResourceNotFoundException("Student", "No se ha encontrado el estudiante por el id (" + id + ") o código (" + code + ").");
        }

        List<SubjectWithNotesModel> notes = this.subjectStudentPort.findAllSubjectWithNotesByStudent(student.getId());
        return notes
                .stream()
                .map(this.calculateFinalNote::calculate)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }

    @Override
    public List<StudentWithAllFinalNoteModel> getAll() {
        List<SubjectStudentWithNotesModel> allNotes = this.subjectStudentPort.findAllSubjectWithNotes();
        Map<Integer, List<SubjectStudentWithNotesModel>> mapByStudentId = allNotes.stream().collect(groupingBy(x -> x.getStudent().getId()));

        return mapByStudentId
                .values()
                .stream()
                .map(notes -> {
                    Student student = notes.get(0).getStudent();
                    return new StudentWithAllFinalNoteModel(
                            student.getId(),
                            student.getName(),
                            notes
                                    .stream()
                                    .map(x -> calculateFinalNote.calculate(x.getSubject(), x.getNotes()))
                                    .filter(Objects::nonNull)
                                    .toList()
                    );
                }).toList();
    }



}
