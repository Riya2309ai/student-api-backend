package com.riya.studentapi.service;

import com.riya.studentapi.entity.Student;
import com.riya.studentapi.repository.StudentRepository;
import org.springframework.stereotype.Service;
import com.riya.studentapi.exception.StudentNotFoundException;

import com.riya.studentapi.dto.StudentRequestDTO;
import com.riya.studentapi.dto.StudentResponseDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

//    // Add Student without DTO
//    public Student saveStudent(Student student) {
//        return studentRepository.save(student);
//    }

    // Add Student with DTO
    public StudentResponseDTO saveStudent(StudentRequestDTO dto) {
        logger.info("Saving student with email: {}", dto.getEmail());

        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());

        Student savedStudent = studentRepository.save(student);

        logger.info(
                "Student saved successfully with id: {}",
                savedStudent.getId()
        );

        StudentResponseDTO responseDTO = new StudentResponseDTO();

        responseDTO.setId(savedStudent.getId());
        responseDTO.setName(savedStudent.getName());
        responseDTO.setEmail(savedStudent.getEmail());
        responseDTO.setAge(savedStudent.getAge());

        return responseDTO;
    }

//    // Get All Students without pagination
//    public List<Student> getAllStudents() {
//        return studentRepository.findAll();
//    }

//    // Get All Students with pagination without sort
//    public Page<Student> getAllStudents(int page, int size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//
//        return studentRepository.findAll(pageable);
//    }

    // Get All Students with pagination with sort
    public Page<Student> getAllStudents(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return studentRepository.findAll(pageable);
    }

    // Get Student By Id
//    public Student getStudentById(Long id) {
//        return studentRepository.findById(id).orElse(null);
//    }

    // Get Student By Id with exception handling method
    public Student getStudentById(Long id) {

         logger.info("Fetching student with id: {}", id);

         Student student=studentRepository.findById(id)
                .orElseThrow(() ->
                        new StudentNotFoundException(
                                "Student with id " + id + " not found"
                        ));

        logger.info("Student fetched successfully with id: {}", id);
        return student;
    }

    // Delete Student
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    // Update Student
    public Student updateStudent(Long id, Student updatedStudent) {
        logger.info("Updating student with id: {}", id);

        Student existingStudent = studentRepository.findById(id).orElse(null);

        if (existingStudent != null) {
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setAge(updatedStudent.getAge());

            Student savedStudent = studentRepository.save(existingStudent);
            logger.info(
                    "Student updated successfully with id: {}",
                    id
            );
            return savedStudent;
        }

//        logger.warn("Student not found with id: {}", id);
//        return null;

        //return exception message rather than returning null
        logger.error("Student not found with id: {}", id);

        throw new StudentNotFoundException(
                "Student with id " + id + " not found"
        );
    }

    public List<Student> searchByName(String keyword) {
        logger.info(
                "Searching students by name keyword: {}",
                keyword
        );

        return studentRepository.findByNameContainingIgnoreCase(keyword);
    }

    public List<Student> searchByEmail(String keyword) {
        logger.info(
                "Searching students by email keyword: {}",
                keyword
        );

        return studentRepository.findByEmailContainingIgnoreCase(keyword);
    }

    public List<Student> getStudentsAgeGreaterThan(
            int age) {
        logger.info(
                "Fetching students with age greater than: {}",
                age
        );

        return studentRepository
                .findStudentsAgeGreaterThan(age);
    }
}