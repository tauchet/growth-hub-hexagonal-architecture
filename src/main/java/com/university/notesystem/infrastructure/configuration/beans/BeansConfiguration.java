package com.university.notesystem.infrastructure.configuration.beans;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(BeansImportSelector.class)
public class BeansConfiguration {
}
