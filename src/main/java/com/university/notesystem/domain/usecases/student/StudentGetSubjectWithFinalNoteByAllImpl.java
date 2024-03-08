package com.university.notesystem.domain.usecases.student;

import com.university.notesystem.domain.model.dtos.StudentWithAllFinalNoteDTO;
import com.university.notesystem.domain.model.dtos.SubjectWithFinalNoteDTO;
import com.university.notesystem.domain.model.entities.Note;
import com.university.notesystem.domain.model.entities.Student;
import com.university.notesystem.domain.model.entities.Subject;
import com.university.notesystem.domain.model.mapper.SubjectWithFinalNoteDTOMapper;
import com.university.notesystem.domain.ports.NotePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@RequiredArgsConstructor
public class StudentGetSubjectWithFinalNoteByAllImpl implements StudentGetSubjectWithFinalNoteByAll {

    private final NotePort notePort;

    @Override
    public List<StudentWithAllFinalNoteDTO> getAll() {

        List<Note> allNotes = this.notePort.findAll();

        Map<Integer, List<Note>> mapByStudentId = allNotes.stream().collect(groupingBy(x -> x.getSubject().getId()));
        return mapByStudentId
                .values()
                .stream()
                .map(notes -> {

                    Student student = notes.get(0).getStudent();
                    Map<Integer, List<Note>> mapBySubjectId = notes.stream().collect(groupingBy(x -> x.getSubject().getId()));
                    List<SubjectWithFinalNoteDTO> finalNotes = mapBySubjectId
                            .values()
                            .stream()
                            .map(subjectNotes -> {
                                Subject subject = subjectNotes.get(0).getSubject();
                                return SubjectWithFinalNoteDTOMapper.mapToSubjectWithFinalNoteDTO(subject, subjectNotes);
                            })
                            .toList();

                    return new StudentWithAllFinalNoteDTO(
                            student.getId(),
                            student.getName(),
                            finalNotes
                    );

                }).toList();
    }

}
