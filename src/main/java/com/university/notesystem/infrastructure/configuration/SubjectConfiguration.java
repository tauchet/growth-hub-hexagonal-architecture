package com.university.notesystem.infrastructure.configuration;

import com.university.notesystem.domain.ports.SubjectPort;
import com.university.notesystem.domain.usecases.subject.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SubjectConfiguration {

    private final SubjectPort subjectPort;

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


}
