package com.example.Maven.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepositry extends JpaRepository<Student, Long> {
//    @Query("SELECT * FROM student WHERE student.email = ?1")


    Optional<Student> findStudentByEmail(String email);
}
