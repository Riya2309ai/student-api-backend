package com.riya.studentapi.dto;

public class DepartmentRequestDTO {            // used for incoming request body

    private String name;

    public DepartmentRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}