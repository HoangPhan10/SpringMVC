package com.example.springbootweb.Exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLSyntaxErrorException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandle {
    @ExceptionHandler(SQLSyntaxErrorException.class)
    public String sqlSyntaxErrorException(SQLSyntaxErrorException ex){
        log.warn(ex.getMessage());
        return "No hacker";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception ex){
        log.warn(ex.getMessage());
        return "No hacker";
    }
}
