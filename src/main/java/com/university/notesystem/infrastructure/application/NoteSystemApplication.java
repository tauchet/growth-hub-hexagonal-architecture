package com.university.notesystem.infrastructure.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.regex.Pattern;

@SpringBootApplication(scanBasePackages = "com.university.notesystem")
public class NoteSystemApplication {

    public static final String USECASES_ROUTE = "com.university.notesystem.domain.usecases";

    public static final String ADAPTERS_ROUTES = "com.university.notesystem.adapters";
    public static final List<Pattern> EXCLUDE_ADAPTERS_ROUTES = List.of(
            Pattern.compile(".*\\.dtos\\..*"),
            Pattern.compile(".*\\.responses\\..*")
    );

    public static void main(String[] args) {
        SpringApplication.run(NoteSystemApplication.class, args);
    }

}
