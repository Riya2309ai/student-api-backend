package com.riya.studentapi.service;

import com.riya.studentapi.entity.Department;
import com.riya.studentapi.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import com.riya.studentapi.dto.DepartmentResponseDTO;

import com.riya.studentapi.dto.DepartmentRequestDTO;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository) {

        this.departmentRepository = departmentRepository;
    }

    //get dept. by Id without dto
//    public Department getDepartmentById(Long id) {
//
//        return departmentRepository.findById(id)
//                .orElse(null);
//    }

    //get dept. by Id with dto
    public DepartmentResponseDTO getDepartmentById(Long id){
        Department department =
                departmentRepository.findById(id)
                        .orElse(null);

        DepartmentResponseDTO dto =
                new DepartmentResponseDTO();

        dto.setId(department.getId());
        dto.setName(department.getName());

        return dto;
    }

    public DepartmentResponseDTO saveDepartment(
            DepartmentRequestDTO dto) {

        Department department = new Department();

        department.setName(dto.getName());

        Department savedDepartment =
                departmentRepository.save(department);

        DepartmentResponseDTO responseDTO =
                new DepartmentResponseDTO();

        responseDTO.setId(savedDepartment.getId());
        responseDTO.setName(savedDepartment.getName());

        return responseDTO;
    }

}