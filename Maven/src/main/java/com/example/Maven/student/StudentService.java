package com.example.Maven.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class StudentService {

    private final StudentRepositry studentRepositry;

    @Autowired
    public StudentService(StudentRepositry studentRepositry) {
        this.studentRepositry = studentRepositry;
    }

    public List<Student> getStudent() {
        return studentRepositry.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepositry.findStudentByEmail(student.getEmail());

        if (studentOptional.isPresent()) {
            throw new IllegalArgumentException("Email is already taken");
        }

        // If the email is not taken, save the student to the database
        studentRepositry.save(student);
    }

    public void deleteStudent(Long studentId) {

        boolean exists = studentRepositry.existsById(studentId);

        if (!exists) {
            throw new IllegalStateException("student withe id " + studentId + "dose not exists");
        }
        studentRepositry.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepositry.findById(studentId)
                .orElseThrow(() -> new NoSuchElementException("Student with ID " + studentId + " does not exist"));

        if (name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }

        if (email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
            // Check if the new email is valid (e.g., using regular expressions)
            if (!isValidEmail(email)) {
                throw new IllegalArgumentException("Invalid email format");
            }

            Optional<Student> existingStudent = studentRepositry.findStudentByEmail(email);
            if (existingStudent.isPresent() && !existingStudent.get().getId().equals(studentId)) {
                throw new IllegalStateException("Email already in use by another student");
            }

            student.setEmail(email);
        }
    }

    private boolean isValidEmail(String email) {
        // Define a regular expression pattern for a basic email format
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";

        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);

        // Check if the email matches the pattern
        return matcher.matches();
    }

}
