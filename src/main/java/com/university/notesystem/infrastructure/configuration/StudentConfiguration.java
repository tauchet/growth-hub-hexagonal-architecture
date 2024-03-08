package com.university.notesystem.infrastructure.configuration;


import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.usecases.student.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StudentConfiguration {

    private final StudentPort studentPort;
    private final NotePort notePort;

    @Bean
    public StudentAll studentAll(){
        return new StudentAllImpl(this.studentPort);
    }

    @Bean
    public StudentRegister studentRegister(){
        return new StudentRegisterImpl(this.studentPort);
    }

    @Bean
    public StudentDelete studentDelete(){
        return new StudentDeleteImpl(this.studentPort);
    }

    @Bean
    public StudentGetAllSubjectWithNotes studentGetAllSubjectWithNotes(){
        return new StudentGetAllSubjectWithNotesImpl(this.studentPort, this.notePort);
    }

    @Bean
    public StudentGetSubjectWithFinalNoteById studentGetAllSubjectWithFinalNote(){
        return new StudentGetSubjectWithFinalNoteByIdImpl(this.studentPort, this.notePort);
    }

    @Bean
    public StudentGetSubjectWithFinalNoteByAll studentGetSubjectWithFinalNoteByAll(){
        return new StudentGetSubjectWithFinalNoteByAllImpl(this.notePort);
    }

}
