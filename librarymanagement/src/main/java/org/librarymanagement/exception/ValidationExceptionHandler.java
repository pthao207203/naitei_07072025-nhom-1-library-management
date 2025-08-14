package org.librarymanagement.exception;

import org.librarymanagement.dto.response.ValidationErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationExceptionHandler {
    // Xử lý validate exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String,String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        error -> error.getField(),
                        error -> error.getDefaultMessage(),
                        (existing, replacement) -> existing
                ));

        return new ValidationErrorResponse(HttpStatus.BAD_REQUEST.value(),"Validation failed", fieldErrors);
    }
    // Xử lý trùng dữ liệu
    @ExceptionHandler(DuplicateFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponse handleDuplicateFieldException(DuplicateFieldException ex) {
        return ValidationErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Duplicate field")
                .fieldErrors(ex.getFieldErrors())
                .build();
    }
}
