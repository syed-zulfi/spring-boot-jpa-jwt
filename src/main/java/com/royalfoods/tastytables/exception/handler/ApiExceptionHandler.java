package com.royalfoods.tastytables.exception.handler;

import com.royalfoods.tastytables.data.dto.error.*;
import com.royalfoods.tastytables.exception.*;
import com.royalfoods.tastytables.factory.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.*;

@RestControllerAdvice
@AllArgsConstructor
public class ApiExceptionHandler {
    ApiErrorDtoBuilder errorDtoBuilder;
    @ExceptionHandler({NullObjectException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleNullObjectException(NullObjectException nob) {
        return errorDtoBuilder.withMessage("Error occurred")
                .withDetailMessage(nob.getLocalizedMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .build().get();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleFieldValidationException(MethodArgumentNotValidException fieldInvalidExp){
        List<SubError> fieldErrors = new ArrayList<>();
        fieldInvalidExp.getFieldErrors().stream().forEach((fe)->{
            FieldValidationError fieldError = FieldValidationError.builder()
                                                .fieldName(fe.getField())
                                                .fieldError(fe.getDefaultMessage())
                                                .entityName(fe.getObjectName())
                                                .build();
            fieldErrors.add(fieldError);
        });
        return errorDtoBuilder.withHttpStatus(HttpStatus.BAD_REQUEST)
                .withMessage("Validation failed for fields "+fieldInvalidExp.getFieldErrors().stream().map(e->e.getField()).collect(Collectors.toList()))
                .withDetailMessage(fieldInvalidExp.getLocalizedMessage())
                .withSubErrors(fieldErrors)
                .build().get();
    }

    @ExceptionHandler(UniqueRecordConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleUniqueViolationException(UniqueRecordConstraintViolationException exp) {
        return errorDtoBuilder.withMessage("Unique record violation occurred")
                .withDetailMessage(exp.getLocalizedMessage())
                .withHttpStatus(HttpStatus.BAD_REQUEST)
                .build().get();
    }

    @ExceptionHandler(ApiSecurityException.class)
    @ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
    public ApiError handleAuthenticationException(ApiSecurityException apiSecurityException) {
        return errorDtoBuilder.withHttpStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
                .withDetailMessage(apiSecurityException.getCause().getLocalizedMessage())
                .withMessage(apiSecurityException.getMessage())
                .build().get();
    }
}
