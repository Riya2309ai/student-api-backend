package com.riya.studentapi.controller;

import com.riya.studentapi.entity.Student;
import com.riya.studentapi.service.StudentService;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.riya.studentapi.dto.StudentRequestDTO;
import com.riya.studentapi.dto.StudentResponseDTO;

import org.springframework.data.domain.Page;

import com.riya.studentapi.response.ApiResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Student APIs",
        description = "CRUD APIs for Student Management"
)
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

//    // Add Student without DTO
//    @PostMapping
//    public Student addStudent(@Valid @RequestBody Student student) {
//        return studentService.saveStudent(student);
//    }

    //addStudent with using DTO
    @Operation(
            summary = "Add new student",
            description = "Creates a new student record"
    )
    @PostMapping
    public StudentResponseDTO addStudent(
            @Valid @RequestBody StudentRequestDTO studentRequestDTO) {

        return studentService.saveStudent(studentRequestDTO);
    }

//    // Get All Students without pagination
//    @GetMapping
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }

//    // Get All Students with pagination without sort
//    @GetMapping
//    public Page<Student> getAllStudents(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size) {
//
//        return studentService.getAllStudents(page, size);
//    }


    // Get All Students with pagination with sort
    @Operation(   //for swagger ai documentation
            summary = "Get students",
            description = "get all students"
    )
    @GetMapping
    public Page<Student> getAllStudents(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return studentService.getAllStudents(
                page,
                size,
                sortBy,
                direction
        );
    }

//    // Get Student By Id without Standard API response
//    @GetMapping("/{id}")
//    public Student getStudentById(@PathVariable Long id) {
//        return studentService.getStudentById(id);
//    }


    // Get Student By Id without Standard API response
    @Operation(
            summary = "Get student by ID",
            description = "Fetches a student using student ID"
    )
    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(
            @PathVariable Long id) {

        Student student =
                studentService.getStudentById(id);

        return new ApiResponse<>(
                true,
                "Student fetched successfully",
                student
        );
    }

    // Delete Student
    @Operation(
            summary = "Delete student",
            description = "Deletes student using ID"
    )
    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "Student deleted successfully";
    }

    // Update Student
    @Operation(
            summary = "update student",
            description = "update student using ID"
    )
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id,
                                 @RequestBody Student student) {

        return studentService.updateStudent(id, student);
    }

    @Operation(
            summary = "search student",
            description = "search student using name"
    )
    @GetMapping("/search/name")
    public List<Student> searchByName(
            @RequestParam String keyword) {

        return studentService.searchByName(keyword);
    }

    @Operation(
            summary = "search student",
            description = "search student using email"
    )
    @GetMapping("/search/email")
    public List<Student> searchByEmail(
            @RequestParam String keyword) {

        return studentService.searchByEmail(keyword);
    }

    @Operation(
            summary = "get students greater than given age",
            description = "get students greater than given age"
    )
    @GetMapping("/age-greater")
    public List<Student> getStudentsAgeGreaterThan(
            @RequestParam int age) {

        return studentService
                .getStudentsAgeGreaterThan(age);
    }
}