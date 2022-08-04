package com.royalfoods.tastytables.factory;

import com.royalfoods.tastytables.data.dto.error.*;
import com.royalfoods.tastytables.factory.abstrcts.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
public class ApiErrorDtoBuilder extends AbstractResponseBuilderFactory {
    private List<SubError> subErrors;
    private String detailMessage;
    private ApiError apiError;
    private HttpStatus status;

    public ApiErrorDtoBuilder withHttpStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

   public ApiErrorDtoBuilder withMessage(String message) {
        this.message = message;
        return this;
   }

   public ApiErrorDtoBuilder withDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
   }

   public ApiErrorDtoBuilder withSubErrors(List<SubError> subErrors){
        this.subErrors = subErrors;
        return this;
   }
    @Override
    public Optional<ApiError> build() {
        apiError = new ApiError(status,message);
        apiError.detailMessage(detailMessage).subErrors(subErrors);
        return Optional.ofNullable(apiError);
    }
}
