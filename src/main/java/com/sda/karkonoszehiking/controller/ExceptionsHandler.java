/*
package com.sda.karkonoszehiking.controller;

import com.sda.karkonoszehiking.exception.HikeNotFoundException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(HikeNotFoundException.class)
    public ProblemDetail handleObjectNotFoundException(HikeNotFoundException exception){
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404), exception.getMessage());
    }
}
*/
