package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.StudentWithAllFinalNoteModel;
import com.university.notesystem.domain.model.SubjectStudentWithNotesModel;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.mapper.SubjectWithFinalNoteDTOMapper;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class StudentGetSubjectWithFinalNoteByAllImpl implements StudentGetSubjectWithFinalNoteByAll {

    private final SubjectStudentPort subjectStudentPort;

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
                                    .map(x -> SubjectWithFinalNoteDTOMapper.mapToSubjectWithFinalNoteDTO(x.getSubject(), x.getNotes()))
                                    .toList()
                    );
                }).toList();
    }

}
