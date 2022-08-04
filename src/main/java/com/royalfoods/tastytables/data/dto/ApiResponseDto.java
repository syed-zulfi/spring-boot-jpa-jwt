package com.royalfoods.tastytables.data.dto;

import lombok.*;
import org.springframework.http.*;

import java.time.*;

@Data
public class ApiResponseDto<T> extends AbstractResponseDto{
    protected HttpStatus status;
    protected T body;

    protected ApiResponseDto(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiResponseDto(HttpStatus status){
        this();
        this.status = status;
    }

    public ApiResponseDto(HttpStatus status, String message) {
        this();
        this.message = message;
    }

    public ApiResponseDto(HttpStatus status, String message, T body) {
        this();
        this.status = status;
        this.body = body;
        this.message = message;
    }
}
