package com.mosa.hogwartsartifactsonline.exception;

import com.mosa.hogwartsartifactsonline.system.Result;
import com.mosa.hogwartsartifactsonline.system.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.access.AccessDeniedException;
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


    @ExceptionHandler(ObjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // it's optional
    Result handleObjectNotFoundException(ObjectNotFoundException e) {

        return new Result(false, StatusCode.NOT_FOUND, e.getMessage());
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
        return new Result(false, StatusCode.INVALID_ARGUMENT, "Provided arguments are invalid, see data for details.", map);
    }


    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // it's return in the headers
    Result handleAuthenticationException(Exception e) {

        return new Result(false, StatusCode.UNAUTHORIZED, "username or password is incorrect.", e.getMessage());
    }



    @ExceptionHandler(AccountStatusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // it's return in the headers
    Result handleAccountStatusException(AccountStatusException e) {

        return new Result(false, StatusCode.UNAUTHORIZED, "User account is abnormal.", e.getMessage());
    }


    @ExceptionHandler(InvalidBearerTokenException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) // it's return in the headers
    Result handleInvalidBearerTokenException(InvalidBearerTokenException e) {

        return new Result(false, StatusCode.UNAUTHORIZED, "The access token provided is expired, revoked, malformed or invalid for other reasons.", e.getMessage());
    }


    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // it's return in the headers
    Result handleAccessDeniedException(AccessDeniedException e) {

        return new Result(false, StatusCode.FORBIDDEN, "No permission.", e.getMessage());
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) // it's return in the headers
    Result handleOtherException(Exception e) {

        return new Result(false, StatusCode.INTERNAL_SERVER_ERROR, "A server internal error occurs.", e.getMessage());
    }


}
