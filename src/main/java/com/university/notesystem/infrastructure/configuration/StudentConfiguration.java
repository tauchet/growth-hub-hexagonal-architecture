package com.university.notesystem.infrastructure.configuration;


import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.student.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StudentConfiguration {

    private final StudentPort studentPort;
    private final SubjectStudentPort subjectStudentPort;

    @Bean
    public StudentGeneralManager studentGeneralManager(){
        return new StudentGeneralManagerImpl(this.studentPort);
    }

    @Bean
    public StudentGetAllSubjectWithNotes studentGetAllSubjectWithNotes(){
        return new StudentGetAllSubjectWithNotesImpl(this.studentPort, this.subjectStudentPort);
    }

    @Bean
    public StudentGetSubjectWithFinalNoteById studentGetAllSubjectWithFinalNote(){
        return new StudentGetSubjectWithFinalNoteByIdImpl(this.studentPort, this.subjectStudentPort);
    }

    @Bean
    public StudentGetSubjectWithFinalNoteByAll studentGetSubjectWithFinalNoteByAll(){
        return new StudentGetSubjectWithFinalNoteByAllImpl(this.subjectStudentPort);
    }

}
