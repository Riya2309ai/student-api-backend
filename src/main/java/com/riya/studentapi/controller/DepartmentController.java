package com.riya.studentapi.controller;

import com.riya.studentapi.entity.Department;
import com.riya.studentapi.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import com.riya.studentapi.dto.DepartmentResponseDTO;

import com.riya.studentapi.dto.DepartmentRequestDTO;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

//    @GetMapping("/{id}")
//    public Department getDepartmentById(
//            @PathVariable Long id) {
//
//        return departmentService.getDepartmentById(id);
//    }


    //return entity and not via dto
//    @GetMapping("/{id}")
//    public Department getDepartmentById(
//            @PathVariable Long id) {
//
//        Department department =
//                departmentService.getDepartmentById(id);
//
//        System.out.println(
//                department.getStudents().size()
//        );
//
//        return department;
//    }

    //returning dto instead of direct entity
    @GetMapping("/{id}")
    public DepartmentResponseDTO getDepartmentById(
            @PathVariable Long id) {

        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public DepartmentResponseDTO saveDepartment(
            @RequestBody DepartmentRequestDTO dto) {

        return departmentService.saveDepartment(dto);
    }

}