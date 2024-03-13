package com.university.notesystem.infrastructure.configuration;

import com.university.notesystem.domain.usecases.UseCase;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.university.notesystem.domain.usecases",
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = UseCase.class)
)
public class UseCaseConfiguration {
}
