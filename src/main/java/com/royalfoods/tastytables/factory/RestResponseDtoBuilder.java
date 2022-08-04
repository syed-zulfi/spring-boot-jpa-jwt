package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.*;
import com.royalfoods.tastytables.factory.abstrcts.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.Optional;

public class RestResponseDtoBuilder<T> extends AbstractResponseBuilderFactory {
    private HttpStatus httpStatus;
    private T body;

    @Override
    public Optional<ApiResponseDto<T>> build() {

        return ServiceResponseBuilder.create(httpStatus,message,body).build();
    }

    public RestResponseDtoBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    public RestResponseDtoBuilder withBody(T body) {
        this.body = body;
        return this;
    }

    public RestResponseDtoBuilder withHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        return this;
    }

}
