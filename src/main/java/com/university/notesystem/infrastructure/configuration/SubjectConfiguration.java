package com.university.notesystem.infrastructure.configuration;

import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.ports.SubjectStudentPort;
import com.university.notesystem.domain.usecases.subject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SubjectConfiguration {

    private final SubjectPort subjectPort;
    private final StudentPort studentPort;
    private final NotePort notePort;
    private final SubjectStudentPort subjectStudentPort;

    @Bean
    public SubjectAll subjectAll(){
        return new SubjectAllImpl(this.subjectPort);
    }

    @Bean
    public SubjectRegister subjectRegister(){
        return new SubjectRegisterImpl(this.subjectPort);
    }

    @Bean
    public SubjectDelete subjectDelete(){
        return new SubjectDeleteImpl(this.subjectPort);
    }

    @Bean
    public SubjectRegisterStudent subjectRegisterStudent(){
        return new SubjectRegisterStudentImpl(this.studentPort, this.subjectPort, this.subjectStudentPort, this.notePort);
    }

    @Bean
    public SubjectUpdateStudentNotes subjectUpdateStudentNotes(){
        return new SubjectUpdateStudentNotesImpl(this.studentPort, this.subjectPort, this.subjectStudentPort, this.notePort);
    }

}
