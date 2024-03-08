package com.university.notesystem.infrastructure.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.university.notesystem")
public class NoteSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteSystemApplication.class, args);
    }

}
