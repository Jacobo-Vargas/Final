package com.example.mercadouq.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Manejar el error de tipo de argumento de método, devolviendo un Bad Request con un mensaje de error
        String errorMessage = "El valor '" + ex.getValue() + "' no es válido para el parámetro '" + ex.getName() + "'.";
        return ResponseEntity.badRequest().body(errorMessage);
    }
}
