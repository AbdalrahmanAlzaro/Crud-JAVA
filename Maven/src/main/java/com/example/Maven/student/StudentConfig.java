package com.example.Maven.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (
            StudentRepositry repositry){
        return args -> {
            Student mariam = new Student(
//                    1L,
                    "mariam",
                    "mariam@gmail.com",
                    LocalDate.of(2000, Month.APRIL, 5));

            Student abd = new Student(
//                    1L,
                    "abd",
                    "abd@gmail.com",
                    LocalDate.of(2001, Month.APRIL, 5));

            repositry.saveAll(
                    List.of(mariam, abd)
            );

        };
    }
}
