package com.riya.studentapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "students")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0")
    private int age;



    //Since we have added Lombok library so it will handle all the constructor, getter, setter automatically so no need to write them manually here

//    public Student() {
//    }

//    public Student(Long id, String name, String email, int age) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//    }

//    public Long getId() {
//        return id;
//    }

//    public void setId(Long id) {
//        this.id = id;
//    }

//    public String getName() {
//        return name;
//    }

//    public void setName(String name) {
//        this.name = name;
//    }

//    public String getEmail() {
//        return email;
//    }

//    public void setEmail(String email) {
//        this.email = email;
//    }

//    public int getAge() {
//        return age;
//    }

//    public void setAge(int age) {
//        this.age = age;
//    }
}