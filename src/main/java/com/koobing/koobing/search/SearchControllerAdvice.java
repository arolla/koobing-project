package com.koobing.koobing.search;

import com.koobing.koobing.search.service.IllegalDateException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SearchControllerAdvice {
    @ResponseBody
    @ExceptionHandler(IllegalDateException.class)
    public ResponseEntity<?> handleControllerException(HttpServletRequest request, Throwable ex) {
        var message = String.format("failed to search available hostels: %s", ex.getMessage());
        return new ResponseEntity<>(new MyErrorBody(HttpStatus.BAD_REQUEST.value(), message), HttpStatus.BAD_REQUEST);
    }

    private record MyErrorBody(int value, String message) {
    }
}
