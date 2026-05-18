package com.riya.studentapi.handler;

import com.riya.studentapi.exception.StudentNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.riya.studentapi.response.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    //getStudentById without standard api response structure
//    @ExceptionHandler(StudentNotFoundException.class)
//    public ResponseEntity<String> handleStudentNotFoundException(
//            StudentNotFoundException ex) {
//
//        logger.error("Exception occurred: {}", ex.getMessage());
//
//        return new ResponseEntity<>(
//                ex.getMessage(),
//                HttpStatus.NOT_FOUND
//        );
//    }

    //getStudentById with standard api response structure
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleStudentNotFoundException(
            StudentNotFoundException ex) {

        logger.error("Exception occurred: {}", ex.getMessage());

        ApiResponse<Object> response =
                new ApiResponse<>(
                        false,
                        ex.getMessage(),
                        null
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}