package com.javaweb.controllerAdvice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        // Xử lý ngoại lệ chung
        return ResponseEntity.status(500).body("Đã xảy ra lỗi: " + ex.getMessage());
    }
}

