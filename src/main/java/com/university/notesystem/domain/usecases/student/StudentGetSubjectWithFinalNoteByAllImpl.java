package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.adapters.driven.h2dbadapter.mapper.NoteMapper;
import com.university.notesystem.domain.model.dtos.StudentWithAllFinalNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectStudentWithNotesDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithFinalNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithNotesDTO;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.mapper.SubjectWithFinalNoteDTOMapper;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class StudentGetSubjectWithFinalNoteByAllImpl implements StudentGetSubjectWithFinalNoteByAll {

    private final SubjectStudentPort subjectStudentPort;

    @Override
    public List<StudentWithAllFinalNoteDTO> getAll() {

        List<SubjectStudentWithNotesDTO> allNotes = this.subjectStudentPort.findAllSubjectWithNotes();
        Map<Integer, List<SubjectStudentWithNotesDTO>> mapByStudentId = allNotes.stream().collect(groupingBy(x -> x.getStudent().getId()));

        return mapByStudentId
                .values()
                .stream()
                .map(notes -> {
                    Student student = notes.get(0).getStudent();
                    return new StudentWithAllFinalNoteDTO(
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
