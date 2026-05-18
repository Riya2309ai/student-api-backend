package com.riya.studentapi.repository;

import com.riya.studentapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContainingIgnoreCase(String keyword);

    List<Student> findByEmailContainingIgnoreCase(String keyword);

    @Query("SELECT s FROM Student s WHERE s.age > :age")       // here Student = Entity class NOT table name
    List<Student> findStudentsAgeGreaterThan(int age);

}