package com.empresa.proyeco.empresa.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;



import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Manejar excepciones genéricas
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // Código HTTP 500
    @ResponseBody
    public Map<String, String> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Ocurrió un error interno");
        response.put("message", ex.getMessage());
        return response;
    }

    // Manejar excepciones personalizadas
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Código HTTP 400
    @ResponseBody
    public Map<String, String> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Solicitud incorrecta");
        response.put("message", ex.getMessage());
        return response;
    }

    // Manejar excepciones de recurso no encontrado
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Código HTTP 404
    @ResponseBody
    public Map<String, String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Recurso no encontrado");
        response.put("message", ex.getMessage());
        return response;
    }

}
