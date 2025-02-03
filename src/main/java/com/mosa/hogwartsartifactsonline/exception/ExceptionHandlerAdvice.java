package com.mosa.hogwartsartifactsonline.exception;

import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {


    @ExceptionHandler(ArtifactNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // it's optional
    Result handleArtifactNotFoundException(ArtifactNotFoundException e) {

        return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(WizardNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    Result handleWizardNotFoundException(WizardNotFoundException e) {

        return new Result(false, StatusCode.NOT_FOUND, e.getMessage(), null);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Result handleValidationException(MethodArgumentNotValidException e) {

        List<ObjectError> errors = e.getBindingResult().getAllErrors();

        Map<String, String> map = new HashMap<>(errors.size());

        errors.forEach((error) -> {
            String key = ((FieldError) error).getField();
            String val = error.getDefaultMessage();
            map.put(key, val);
        });
        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided arguments are invalid, see data for detail.", map);

    }

}
