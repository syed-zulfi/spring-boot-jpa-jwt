package com.royalfoods.tastytables.data.dto.error;


import com.fasterxml.jackson.annotation.*;
import com.royalfoods.tastytables.data.dto.*;
import lombok.*;
import org.springframework.http.*;

import java.util.*;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError extends ApiResponseDto {
private List<SubError> subErrors;
private String detailsMessage;
private ApiError(){
    super();
}

public ApiError(HttpStatus status, String message){
    this();
    this.status = status;
    this.message = message;
}

public ApiError detailMessage(String detailsMessage){
    this.detailsMessage = detailsMessage;
    return this;
}

public ApiError subErrors(List<SubError> subErrors){
    this.subErrors = subErrors;
    return this;
}
}
