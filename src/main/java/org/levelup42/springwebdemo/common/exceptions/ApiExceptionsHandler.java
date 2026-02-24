package org.levelup42.springwebdemo.common.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.levelup42.springwebdemo.product.domain.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionsHandler {

    private final View error;

    public ApiExceptionsHandler(View error) {
        this.error = error;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorMessage badRequest(HttpServletRequest request, MethodArgumentNotValidException exception) {
        Map<String,String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach((error)->{
                errors.put(error.getField(), error.getDefaultMessage());
       });

        return new ErrorMessage(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                request.getRequestURI(),
                errors
               );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseBody
    public ErrorMessage notFound(HttpServletRequest request, Exception exception) {
        return new ErrorMessage(
                exception.getMessage(),
                exception.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }


}
