package com.mosa.hogwartsartifactsonline.exception;

import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // it's optional
    Result handleArtifactNotFoundException(ArtifactNotFoundException e) {

        return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
    }

}
