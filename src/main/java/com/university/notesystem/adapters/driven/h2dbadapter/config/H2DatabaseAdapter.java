package com.university.notesystem.adapters.driven.h2dbadapter.config;

import com.university.notesystem.adapters.driven.h2dbadapter.repository.NoteRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.StudentRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.repository.SubjectRepository;
import com.university.notesystem.adapters.driven.h2dbadapter.service.NoteService;
import com.university.notesystem.adapters.driven.h2dbadapter.service.StudentService;
import com.university.notesystem.adapters.driven.h2dbadapter.service.SubjectService;
import com.university.notesystem.domain.ports.NotePort;
import com.university.notesystem.domain.ports.StudentPort;
import com.university.notesystem.domain.ports.SubjectPort;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.university.notesystem.adapters.driven.h2dbadapter")
@EntityScan(basePackages = "com.university.notesystem.adapters.driven.h2dbadapter")
@RequiredArgsConstructor
public class H2DatabaseAdapter  {

    private final SubjectRepository subjectRepository;
    private final NoteRepository notesRepository;
    private final StudentRepository studentRepository;

    @Bean
    public SubjectPort enableSubjectPort() {
        return new SubjectService(this.subjectRepository);
    }

    @Bean
    public NotePort enableNotesPort() {
        return new NoteService(this.notesRepository);
    }

    @Bean
    public StudentPort enableStudentPort() {
        return new StudentService(this.studentRepository);
    }


}
